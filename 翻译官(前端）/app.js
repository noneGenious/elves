//that.js
App({

  onLaunch: function () {
    var that = this
    console.log(that)
    // 展示本地存储能力

    that.globalData._id = wx.getStorageSync("_id")
    that.globalData.userInfo = wx.getStorageSync('userInfo')
    var history = wx.getStorageSync('history')
    console.log(123)
    console.log(history)
    if( history instanceof Array) {
      that.globalData.history = history
    } 
    // console.log(that.globalData._id)
    // wx.getStorage({
    //   key: '_id',
    //   success: function (res) {
    //     // console.log('this is log')
    //     // console.log(res)
    //     if (res.data != '') {
    //       // console.log('set _id')
    //       that.globalData._id = res.data
    //       // console.log(that.globalData._id)
    //     }
    //   }
    // })
    // console.log(that.globalData._id)

    /*
    wx.checkSession({
      success: res => {
        if (that.globalData._id == '') {
          // login(that.globalData._id)
        }

      },
      fail: function () {
        // login(that.globalData._id)
      }
    })
*/

    // 登录
    // wx.login({
    //   success: res => {
    //     // 发送 res.code 到后台换取 openId, sessionKey, unionId
    //   }
    // })
    // 获取用户信息

    /*
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              console.log(res)
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })*/
  },
  globalData: {
    userInfo: null,
    _id: '',
    dict: [],
    dict_size: 0,
    history: []
  },
  
})

// function login(_id) {
//   var that = getApp()
//   wx.login({
//     success: res => {
//       wx.request({
//         url: 'http://112.74.189.46:8080/WeChatWeb/Login',
//         data: {
//           _id: _id,
//           js_code: res.code
//         },
//         success: ret => {
//           console.log(ret)
//           if (ret.data._id != null) {
//             console.log(ret)
//             that.globalData._id = ret.data._id

//             wx.setStorageSync("_id", ret.data.data)
//           } else {

//           }
//         }
//       })
//     }
//   })
// }