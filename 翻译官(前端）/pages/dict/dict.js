const app = getApp()


Page({

  data: {
    dict: [],
  },


  onLoad: function (options) {
    const that = this

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
    const that = this
    getDict()
    that.setData({
      dict: app.globalData.dict,
      dict_size: app.globalData.dict_size
    })

    if (that.data.dict == undefined || that.data.dict.length <= 0) {
      wx.showModal({
        title: '没有单词',
        content: '你的单词本没有单词哦，快去添加单词吧',
        succeed: res => {
          //TODO
          wx.switchTab({
            url: '/pages/translate/translate',
          })
        }
      })
    } else {



    }
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

  onWordInfo: e => {
    console.log(e)
    wx.navigateTo({
      url: '/pages/word/word?q=' + e.currentTarget.dataset.query
    })

  }
})

function getDict() {
  var app = getApp()
  var that = this
  wx.request({
    url: 'http://127.0.0.1:8080/WeChatWeb/GetDict',
    data: {
      _id: app.globalData._id
    },
    success: res => {
      console.log(res.data)
      app.globalData.dict = JSON.parse(res.data.dict)
      app.globalData.dict_size = res.data.size
    }
  })
}