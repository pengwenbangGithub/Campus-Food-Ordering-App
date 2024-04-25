package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
@Api(tags = "菜单相关接口")
public class DishController {


    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        dishService.saveWithFlavor(dishDTO);
        // 清理缓存
        String key = "dish_" + dishDTO.getCategoryId();
        clearCache(key);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品数据分页查询")
    public Result<PageResult> getPage(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{dishId}")
    @ApiOperation("根据菜品id查询")
    public Result<DishVO> getById(@PathVariable Long dishId) {
        DishVO dishVO = dishService.getByIdWithFlavor(dishId);
        return Result.success(dishVO);
    }

    @DeleteMapping
    @ApiOperation("菜品的批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        dishService.deleteByIds(ids);

        // 将所有的菜品缓存都删除(以dish_开头的)
        clearCache("dish_*");
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改菜品信息")
    public Result update(@RequestBody DishVO dishVO) {
        log.info("修改菜品信息, {}", dishVO);
        dishService.update(dishVO);

        // 将所有的菜品缓存都删除(以dish_开头的)
        clearCache("dish_*");
        return Result.success();
    }

    /**
     * 清理缓存数据
     *
     * @param pattern
     */
    private void clearCache(String pattern) {
        // 将所有的菜品缓存都删除(以dish_开头的)
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
