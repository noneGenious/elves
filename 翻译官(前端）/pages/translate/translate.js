var base64 = require("../images/base64");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    index: 0,
    msg: 'this is a template',
    time: (new Date()).toString(),
    // searchbar
    inputShowed: false,
    inputVal: "",
    tip: "在此输入要翻译的文本",
    array: ['中文', 'English', '日文', '韩文', '法文', '俄文', '西班牙文', '葡萄牙文', '越南文'],
    code: ['zh-CHS', 'en', 'ja', 'ko', 'fr', 'ru', 'es', 'pt', 'vi'],
    fromCode: 'auto',
    toCode: 'en',
    from: '中文',
    to: 'English',
    history: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      icon: base64.icon20
    });

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

    const app = getApp()
    const that = this

    wx.checkSession({
      success: res => {
        if (app.globalData._id == "") {
          wx.switchTab({
            url: '/pages/login/login',
          })
        }

      },
      fail: res => {
        wx.navigateTo({
          url: '/pages/login/login',
        })
      }
    })
    // getDict()
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    const app = getApp()
    const that = this
    that.setData({history: app.globalData.history})
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


  // 搜索栏js
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
  onTip: function () {
    this.setData({
      tip: ""
    })
  },
  onTranslate: function (e) {
    console.log(e)
    const that = this
    var text = e.detail.value
    if (!isEmpty(text)) {
      wx.navigateTo({
        url: '/pages/word/word?q=' + encodeURI(text) + '&from=' + that.data.fromCode + '&to=' + that.data.toCode,
      })
    }
  },

  openCamera: function (e) {
    wx.navigateTo({
      url: '/pages/camera/camera',
    })
  },

  onVoice: function (e) {
    wx.navigateTo({
      url: '/pages/audio/audio',
    })
  },

  changeLanguageFrom: function(e) {
    var that = this
    that.setData({ fromCode: that.data.code[e.detail.value], from: that.data.array[e.detail.value]})
  },

  changeLanguageTo: function(e) {
    var that = this
    that.setData({ toCode: that.data.code[e.detail.value], to: that.data.array[e.detail.value] })
  },

  checkHistory: function(e) {
    console.log(e)
    wx.navigateTo({
      url: '/pages/word/word?q=' + e.currentTarget.dataset.query,
    })
  }
})

function isEmpty(str) {
  if (str == "") return true;
  var regu = "^[ ]+$";
  var re = new RegExp(regu);
  return re.test(str);
}

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