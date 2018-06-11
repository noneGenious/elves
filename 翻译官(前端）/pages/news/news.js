var base64 = require("../images/base64");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    inputShowed: false,
    inputVal: "",
    news: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    var app = getApp()
    console.log(app.globalData._id)
    console.log(that.data.news.length)
    wx.request({
      url: 'http://127.0.0.1:8080/WeChatWeb/ShowNews',
      data: {
        _id: app.globalData._id,
        skip: that.data.news.length
      },
      success: res => {
        console.log(res.data)
        that.setData({
          news: res.data.news
        })
      },
      fail: res => {
        console.log(res)
      }
    })

    

    this.setData({
      icon20: base64.icon20,
      icon60: base64.icon60,

    });

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
    var that = this
    var app = getApp()
    wx.request({
      url: 'http://127.0.0.1:8080/WeChatWeb/ShowNews',
      data: {
        _id: app.globalData._id,
        skip: that.data.news.length
      },
      success: res => {
        that.setData({
          news: res.data.news
        })
      }
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this
    var app = getApp()
    wx.request({
      url: 'http://127.0.0.1:8080/WeChatWeb/ShowNews',
      data: {
        _id: app.globalData._id,
        skip: that.data.news.length
      },
      success: res => {
        that.setData({
          news: that.data.news.concat(res.data.news)
        })
      }
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  },
  // searchbar
  showInput: function () {
    this.setData({
      inputShowed: true
    });
  },
  hideInput: function () {
    this.setData({
      inputVal: "",
      inputShowed: false
    });
  },
  clearInput: function () {
    this.setData({
      inputVal: ""
    });
  },
  inputTyping: function (e) {
    this.setData({
      inputVal: e.detail.value
    });
  },

  newsInfo: function (event) {
    var _id = event.currentTarget.dataset._id
    // console.log(event.currentTarget)
    wx.navigateTo({
      url: '/pages/article/article?_id=' + _id,
    })
  }

})