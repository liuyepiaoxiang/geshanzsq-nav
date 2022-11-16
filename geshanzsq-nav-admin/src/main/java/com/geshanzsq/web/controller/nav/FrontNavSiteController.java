package com.geshanzsq.web.controller.nav;

import com.geshanzsq.common.annotation.Log;
import com.geshanzsq.common.core.controller.BaseController;
import com.geshanzsq.common.core.domain.AjaxResult;
import com.geshanzsq.common.core.page.TableDataInfo;
import com.geshanzsq.common.enums.BusinessType;
import com.geshanzsq.common.utils.SecurityUtils;
import com.geshanzsq.nav.domain.NavSite;
import com.geshanzsq.nav.service.INavPictureService;
import com.geshanzsq.nav.service.INavSiteService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 导航网站信息Controller
 * 
 * @author geshanzsq
 * @date 2020-07-11
 */
@RestController
@RequestMapping("/frontNav/site")
public class FrontNavSiteController extends BaseController
{
    @Autowired
    private INavSiteService navSiteService;
    @Autowired
    private INavPictureService navPictureService;

    /**
     * 查询导航网站信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(NavSite navSite)
    {
        startPage();
        List<NavSite> list = navSiteService.selectNavSiteList(navSite);
        return getDataTable(list);
    }


    /**
     * 获取导航网站信息详细信息
     */
    @GetMapping(value = "/{siteId}")
    public AjaxResult getInfo(@PathVariable("siteId") Long siteId)
    {
        return AjaxResult.success(navSiteService.selectNavSiteById(siteId));
    }

    /**
     * 新增导航网站信息
     */
    @Log(title = "导航网站信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody NavSite navSite)
    {
        return toAjax(navSiteService.insertNavSite(navSite));
    }

    /**
     * 修改导航网站信息
     */
    @Log(title = "导航网站信息", businessType = BusinessType.UPDATE)
    @PutMapping("edit")
    public AjaxResult edit(@RequestBody NavSite navSite)
    {
        navSite.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(navSiteService.updateNavSite(navSite));
    }

    /**
     * 删除导航网站信息
     */
    @Log(title = "导航网站信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{siteIds}")
    public AjaxResult remove(@PathVariable Long[] siteIds)
    {
        return toAjax(navSiteService.deleteNavSiteByIds(siteIds));
    }

    /**
     * 网站图片上传
     */
    @Log(title = "导航网站信息-网站图片上传", businessType = BusinessType.OTHER)
    @PostMapping("/uploadSiteImg")
    public AjaxResult uploadSiteImg(@RequestParam("pictureFile") MultipartFile file) {
        if (file == null) {
            return AjaxResult.error("请选择需要上传的图片！");
        }

        // 上传并返回新文件名称
        try {
            String fileName = navPictureService.getUploadFileName(SecurityUtils.getUsername(),"system",file);
            return AjaxResult.success("上传网站图片成功！",fileName);
        } catch (IOException e) {
            logger.error("上传图片出错：",e);
        }
        return AjaxResult.error("上传网站图片失败！");
    }

    /**
     * 获取网站菜单下最大的排序
     */
    @GetMapping("/getSiteMaxOrderNum")
    public AjaxResult getSiteMaxOrderNum(Long menuId) {
        return AjaxResult.success(navSiteService.getSiteMaxOrderNum(menuId));
    }

    /**
     * 更新站点点击量
     */
    @GetMapping("/updateClickCount/{siteId}")
    public AjaxResult updateClickCountBySiteId(@PathVariable("siteId") Long siteId) {
        boolean result = navSiteService.updateClickCountBySiteId(siteId);
        if (result) {
            return AjaxResult.success("更新成功");
        } else {
            return AjaxResult.error("更新失败");
        }
    }

}
