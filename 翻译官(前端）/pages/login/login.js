Page({

  /**
   * 页面的初始数据
   */
  data: {
    
  },

  /**
   * 
   * 
   * 
   * asdfasdfasfasdfqwqW  WWQ W QWaaasdfadsfasdf
   * 
   * 
   * 
   * aadsf
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = getApp()
    var _this = this
    
    // that.globalData._id = wx.getStorageSync("_id")
    // wx.checkSession({
    //   success: res => {
    //     if (that.globalData._id == '') {
    //       login(that.globalData._id)
    //     }

    //   },
    //   fail: function () {
    //     login(that.globalData._id)
    //   }
    // })


  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  },

  onGotUserInfo: function(e) {
    console.log(e.detail)
    var app = getApp()
    app.globalData.userInfo = e.detail.userInfo
    // console.log(wx.getStorageSync('userInfo'))
    wx.setStorageSync('userInfo', e.detail.userInfo)
    // console.log(wx.getStorageSync('userInfo'))
    login(app.globalData._id)

  }
})

function login(_id) {
  var that = getApp()
  wx.login({
    success: res => {
      console.log(res.code)
      wx.request({
        url: 'http://127.0.0.1:8080/WeChatWeb/Login',
        data: {
          _id: _id,
          js_code: res.code
        },
        success: ret => {
          console.log(ret)
          if (ret.data._id != null) {
            console.log(ret)
            that.globalData._id = ret.data._id
            wx.setStorageSync("_id", ret.data._id)
            console.log(ret.data._id)
            wx.navigateBack({
              
            })
          } else {

          }

        }
      })
    }
  })
}