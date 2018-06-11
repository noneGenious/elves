Page({

  data: {
    isEN: true,
    imgText2ZH: [],
    newsContent2ZH: [],
    title2ZH: '',
    transText:'全文翻译',
    avg: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this

    wx.request({
      url: 'http://127.0.0.1:8080/WeChatWeb/GetNews',
      data: {
        _id: options._id
      },
      success: res => {
        console.log(res.data.news)
        that.setData({
          news: res.data.news,
        })
      }
    })


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

  translate: function (e) {
    var that = this
    that.setData({ isEN: !that.data.isEN })
    //全文翻译
    if (that.data.newsContent2ZH.length == 0) {
      var newsContent = that.getContent(that.data.news.news_content)
      wx.request({
        url: 'http://127.0.0.1:8080/WeChatWeb/Translate_B',
        data: {
          from: 'en',
          to: 'zh',
          q: newsContent
        },
        success: function (res) {
          var temp = that.data.news.news_content
          that.setData({ newsContent2ZH: res.data.trans_result })
          console.log(res.data)
        }
      })
    }

    if (that.data.imgText2ZH.length == 0) {
      var imgText = that.getContent(that.data.news.img_text)
      wx.request({
        url: 'http://127.0.0.1:8080/WeChatWeb/Translate_B',
        data: {
          from: 'en',
          to: 'zh',
          q: imgText
        },
        success: function (res) {
          console.log(res.data)
          that.setData({ imgText2ZH: res.data.trans_result })
        }
      })
    }

    if(that.data.title2ZH == '') {
      wx.request({
        url: 'http://127.0.0.1:8080/WeChatWeb/Translate_B',
        data: {
          from: 'en',
          to: 'zh',
          q: that.data.news.news_title
        },
        success: function(res) {
          that.setData({ title2ZH: res.data.trans_result[0].dst})
        }
      })
    }

    //显示逻辑
    if(that.data.isEN){
      that.setData({ transText: '全文翻译'})
    } else{
      that.setData({ transText: 'click to English'})
    }

    wx.pageScrollTo({
      scrollTop: 0,
      duration:300
    })

  },

  getContent(array) {
    var content = ''
    for (var i = 0; i < array.length; ++i) {
      if (!isEmpty(array[i])) {
        content += array[i];
        content += '\n'
      }
    }
    return content
  },


})

function isEmpty(str) {
  if (str == "") return true;
  var regu = "^[ ]+$";
  var re = new RegExp(regu);
  return re.test(str);
}