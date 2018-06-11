// pages/me/me.js
const app = getApp()

Page({
  data: {
    userInfo: {},
    follow_num:"5k",
    fans_num:"20"
  },
  openSet: function () {
    wx.showActionSheet({
      itemList: ['使用帮助', '应用反馈'],
      success: function (res) {
        if (!res.cancel) {
          //在这里写点击后的跳转
          console.log(res.tapIndex)
        }
      }
    });
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  
  editTag: function(e) {
    wx.navigateTo({
      url: '/pages/grid/grid',
    })
  }


})
