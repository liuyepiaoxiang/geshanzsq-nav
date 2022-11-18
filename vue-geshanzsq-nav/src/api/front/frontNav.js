import request from '@/utils/request'

//查询导航
export function getFrontNav() {
  return request({
    url: '/frontNav',
    method: 'get'
  });
}

// 获取导航菜单
export function getFrontMenu() {
  return request({
    url: '/frontNav/getFrontMenu',
    method: 'get'
  });
}

// 获取用户个人的导航菜单
export function getUserFrontMenu(params) {
  return request({
    url: '/frontNav/site/list',
    method: 'get',
    params
  });
}

//搜索站内
export function searchSite(siteName) {
  return request({
    url: '/frontNav/search/' + siteName,
    method: 'get',
  })
}

// 获取关于本站
export function getNavAbout() {
  return request({
    url: '/frontNav/getNavAbout',
    method: 'get'
  })
}

// 获取评论
export function getCommentList() {
  return request({
    url: '/frontNav/comment/getCommentList',
    method: 'get'
  })
}

// 增加导航点击数量
export function updateNavClickCount(siteId) {
  return request({
    url: `/frontNav/site/updateClickCount/${siteId}`,
    method: 'get',
  });
}
// http://43.154.22.73:8080/user/sumClickCount?userWalletAddress=0x0914f881EC583fCD460031A93B135B9a706ADeBE

// 获取用户点击总量
export function getUserClickCount(params) {
  return request({
    url: `/user/sumClickCount`,
    method: 'get',
    params
  });
}

// 增加站点导航信息
export function addSiteInfo(data) {
  return request({
    url: '/frontNav/site/add',
    method: 'post',
    data,
  });
}
