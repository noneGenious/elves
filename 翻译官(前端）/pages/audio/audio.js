var app = getApp()
Page({
  data: {
    j: 1,//帧动画初始图片  
    isSpeaking: false,//是否正在说话  
    voices: [],//音频数组  
    tempFilePath: '',
    from: 'zh-CHS',
    to: 'EN'
  },

  onLoad: function (option) {
    var that = this
    const recorderManager = wx.getRecorderManager()
    recorderManager.onError(function () {
      that.tip("录音失败！")
    })

    recorderManager.onStop(function (res) {
      var date = new Date()
      if (res.duration < 1000) {
        that.tip("录音时间太短了")
      } else {
        that.setData({
          tempFilePath: res.tempFilePath
        })
        that.sendVoice(res.tempFilePath)
        console.log(res)
      }
    })

    recorderManager.onStart(function () {

    })


  },

  tip: function (msg) {
    wx.showModal({
      title: '提示',
      content: msg,
      showCancel: false
    })
  },

  //手指按下  
  touchdown: function () {
    console.log("手指按下了...")
    console.log("new date : " + new Date)
    var _this = this;
    speaking.call(this);
    this.setData({
      isSpeaking: true
    })
    const options = {
      duration: 10000,
      sampleRate: 44100,
      numberOfChannels: 1,
      encodeBitRate: 192000,
      format: 'mp3',
      frameSize: 50
    }
    wx.getRecorderManager().start(options)
  },
  //手指抬起  
  touchup: function () {
    console.log("手指抬起了...")
    this.setData({
      isSpeaking: false,
    })
    clearInterval(this.timer)
    wx.getRecorderManager().stop()
  },

  sendVoice: function (tempFilePath) {
    var that = this
    wx.uploadFile({
      url: 'http://127.0.0.1:8080/WeChatWeb/Audio',
      filePath: tempFilePath,
      name: 'mp3',
      header: {
        'content-type': 'multipart/form-data'
      },
      formData: {
        _id: app.globalData._id,
        from: that.data.from,
        to: that.data.to
      },
      success: function(res) {
        console.log(res.data)
        that.setData({
          data: res.data
        })
      }

    })
  },

  listen: function(e) {
    const innerAudioContext = wx.createInnerAudioContext()
    innerAudioContext.autoplay = true
    innerAudioContext.src = this.data.tempFilePath
    console.log(this.data.tempFilePath)
    innerAudioContext.onPlay(() => {
      console.log('开始播放')
    })
    innerAudioContext.onError((res) => {
      console.log(res.errMsg)
      console.log(res.errCode)
    })
    innerAudioContext.play()
  }
  
})
//麦克风帧动画  
function speaking() {
  var _this = this;
  //话筒帧动画  
  var i = 1;
  this.timer = setInterval(function () {
    i++;
    i = i % 5;
    _this.setData({
      j: i
    })
  }, 500);
}

