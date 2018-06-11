Page({

  data: {
    data: '',
    isWord: false,
    inDict: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const that = this
    const app = getApp()
    console.log(options)
    if (app.globalData.dict.length <= 0) {
      getDict()
    }

    wx.request({
      url: 'http://127.0.0.1:8080/WeChatWeb/Translate_Y',
      data: {
        q: options.q,
        from: options.from,
        to: options.to
      },
      success: function(res) {
      //存储搜索记录
        var history = {
          query: res.data.query,
          translate: res.data.basic.explains
        }
        console.log(res.data)
        app.globalData.history.push(history)
        if(app.globalData.history.length > 10) {
          app.globalData.history.shift()
        }
        var historyArr = app.globalData.history
        wx.setStorage({
          key: 'history',
          data: historyArr,
        })
      //
        that.setData({
          data: res.data
        })
 
        if (res.data.basic.phonetic != undefined) {
          that.setData({ isWord: true})
        }
        var q = res.data.query
        var dict = app.globalData.dict
        for (var i = 0; i < dict.length; ++i) {
          if (q == dict[i].query) {
            that.setData({ inDict: true })
            break;
          }
        }
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

  speak: function(e) { 
    const innerAudioContext = wx.createInnerAudioContext()
    innerAudioContext.autoplay = true
    innerAudioContext.src = this.data.data.speakUrl
    console.log(this.data.data.speakUrl)
    innerAudioContext.onPlay(() => {
      console.log('开始播放')
    })
    innerAudioContext.onError((res) => {
      console.log(res.errMsg)
      console.log(res.errCode)
    })
    innerAudioContext.play()
  },

  onStar: function(e) {


    const app = getApp()
    const that = this

    console.log(e)
    console.log(that.data.data)
    if(that.data.inDict == false) {
      if(app.globalData.dict.length < app.globalData.dict_size) {
        that.setData({ inDict: !that.data.inDict }) 
        console.log("can add word")
        var arr = app.globalData.dict
        arr.push(that.data.data)
        app.globalData.dict = arr
        //TODO
        wx.request({
          url: 'http://127.0.0.1:8080/WeChatWeb/AddToDict',
          data: {
            user_id: app.globalData._id,
            word_id: e.currentTarget.dataset.query
          },
          success: res => {
            console.log(res)
          },
          fail: res => {
            console.log(res)
          }
        })

      } else {
        wx.showModal({
          title: '无法添加',
          content: '你的单词本已经满了，快去扩容吧',
          success: res => {
            if(res.confirm) {
              //TODO
            }
          }
        })


      }
    }

    
  },

  offStar: function(e) {
    const that = this
    var app = getApp()
    var dict = app.globalData.dict
    var q = that.data.data.query

    that.setData({ inDict: !that.data.inDict }) 

    for(var i = 0; i < dict.length; ++i) {
      if (q == dict[i].query) {
        dict.splice(i, 1  )
        break;
      }
    }

    wx.request({
      url: 'http://127.0.0.1:8080/WeChatWeb/DelFromDict',     
      data: {
        user_id: app.globalData._id,
        word_id: e.currentTarget.dataset.query
      }
    })

  },


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
      app.globalData.dict = JSON.parse(res.data.dict)
      app.globalData.dict_size = res.data.size
    }
  })
}