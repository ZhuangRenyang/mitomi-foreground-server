package com.mito.mitomi.foreground.server.mapper;

import com.mito.mitomi.foreground.server.pojo.entity.Commodity;
import com.mito.mitomi.foreground.server.pojo.vo.CommodityDetailVO;
import com.mito.mitomi.foreground.server.pojo.vo.CommodityListItemVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommodityMapper {

    /**
     * 插入商品数据
     *
     * @param commodity 商品数据
     * @return 受影响的行数，成功插入数据，返回值为1
     */
    int insertCommodity(Commodity commodity);

    /**
     * 根据品牌id删除商品数据
     *
     * @param id 期望删除的商品数据的id
     * @return 受影响的行数，当删除成功时，返回1，如果无此id对应的数据，则返回0
     */
    int deleteCommodityById(Long id);

    /**
     * 根据若干个id一次性删除多个商品
     *
     * @param ids 若干个id
     * @return @return 受影响的行数，当删除成功时，返回对应行数的值，如果无ids对应的数据，则返回0
     */
    int deleteCommodityByIds(Long... ids);

    /**
     * 根据id 修改品牌的名称 @Param注解来标记对应的参数名称
     * JVM底层编译运行程序时，默认时不会保存局部变量名称的，由于方法的参数也是局部变量，所以参数的名称在
     * 编译时就消失了，运行时不能保存，导致Mybatis默认情况下多个参数时，是不能能直接使用参数名称对应#{}中
     * 的内容，但是Springboot官方脚手架创建的java项目JVM参数进行了修改，是的方法的局部变量名称也能保存，
     * 所以直接编写变量名就可以对应#{}里面的名称，但是使用阿里云的脚手架创建的项目就没有进行JVM参数的修改，
     * 就不能直接编写变量名称，就会导致程序报错。为了保证程序的运行，最后在参数前加@Param注解来标记参数名称
     *
     * @param id   商品的id
     * @param name 新商品的名称
     * @return 受影响的行数，当修改成功时，返回1，如果无此id对应的数据，则返回0
     */
    int updateCommodityNameById(@Param("id") Long id, @Param("name") String name, @Param("gmtModified")LocalDateTime gmtModified);

    /**
     * 根据id查询商品详情
     *
     * @param id 商品id
     * @return 商品详情
     */
    CommodityDetailVO getCommodityById(Long id);

    /**
     * 查询商品列表
     *
     * @return 品牌列表
     */
    List<CommodityListItemVO> commodityList();

    List<CommodityListItemVO> commodityCategoryList(String category);

    int countCommodityByName(String name);

    int updateCommodityNameById(Commodity commodity);

    /**
     * 根据id查询商品url
     * @param id 商品id
     * @return 商品图片url路径
     */
    String selectCommodityLogoById(Long id);
}
