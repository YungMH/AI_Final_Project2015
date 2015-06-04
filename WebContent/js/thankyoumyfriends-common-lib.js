/**
 * 返回上一頁－用於同一頁面的Panel更換
 * @param $backButtonObject 返回上一頁的按鈕物件
 * @param $fromPanelObject 換頁前(當前頁)Panel的div物件
 * @param $toPanelObject 換頁後(上一頁)Panel的div物件
 * @param callBackFunctionObject 在導頁前，要執行的方法物件
 * @returns 返回上一頁的按鈕物件
 */
function backPrevious4ViewChange( $backButtonObject, $fromPanelObject, $toPanelObject,callBackFunctionObject){
	$backButtonObject.click(function() {
      if($.isFunction(callBackFunctionObject)){
        callBackFunctionObject();
	  }
			
	  $RS.AnimationUtil.switchPanel( $fromPanelObject, $toPanelObject);
	});
  return $backButtonObject;
}

/**
 * 刪除前進行Confirm，詢問是否定確認刪除
 * @param $removeConfirmPanel 記錄Confirm內容的頁面區塊物件
 * @param removeFunctionObject 要進行刪除動作的方法物件
 */
function removeCeonfirmation($removeConfirmPanel,removeFunctionObject) {
	$removeConfirmPanel.dialog({
		resizable: false,
		height:150,
		modal: true,
		buttons: {
			"是": function() {				
				removeFunctionObject();
				$( this ).dialog( "close" );
			},
			"否": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

(function() {
	
  var self = this;
	
  $("a[href='#']").live("click", function() {
    return false;
  });
	
  // 檢查數字格式，輸入值必須為正、負整數或小數或0
  $(":text[constraint~='numberAllowEmpty']")
  .live("keypress", function(event) {

    var chr = String.fromCharCode(event.charCode == undefined ? event.keyCode : event.charCode);
    return event.ctrlKey || (chr < ' ' || /[\d\.-]/.test(chr));
  })
  .live("keyup", function() {
 
    if (!/^[0-9]+(\.[0-9]*)?$/.test($(this).val()) && $(this).val() !== "") {
      $(this).val("");
      $(this).change();
      $(this).focus();
    }
  })
  .live("change", function() {
    
    if (!/^[0-9]+(.[0-9]+)?$/.test($(this).val()) && $(this).val() !== "") {
      $(this).val("");
      $(this).change();
    }
  });

  $(":text[constraint~='email']")
  .live("keypress", function(event) {

    var chr = String.fromCharCode(event.charCode == undefined ? event.keyCode : event.charCode);
    return event.ctrlKey || (chr < ' ' || /[\d\w\._\-@]/.test(chr));
  });
	
  /**
   * 設定必填的function
   */
  var setRequired = function(elem, labelName) {
    removeRequired(elem, labelName);
    elem.attr("constraint", elem.attr("constraint") + " required");
  };

  /**
   * 取消必填的function
   */
  var removeRequired = function(elem, labelName) {
    
    if (labelName) {
      elem.attr("labelName", labelName);
    }
    var constraint = elem.attr("constraint") || "";
    elem.attr("constraint", constraint.replace(/required/, ""));
  };
  
  /**
   * override必填檢查，增加email欄位的檢查
   */
  var tempValid = $RS.requiredValidation; 
  $RS.requiredValidation = function(form) {

	var emailIsValid = true;
	var phoneIsValid = true;
	  
	(function() {
		
		var labelNames = [];
		var invalidElements = [];
		  
	    var invalidEmails = form.find("[constraint~='email']").filter(function() {
	      return !/^$/.test($(this).val()) && !/^[\w\-\.]+@[\w\-\.]+\.[\w\-\.]+$/.test($(this).val());
	    });
	    
	    invalidEmails.each(function() {
	      labelNames.push($(this).attr("labelname"));
	      invalidElements.push($(this));
	    });
	    
	    if (invalidEmails.size() > 0) {
	      emailIsValid = false;
	      emailInvalidCallback(labelNames, invalidElements); 
	    }
	}());
	
	(function() {
		
		var labelNames = [];
		var invalidElements = [];
		  
	    var invalidPhones = form.find("[constraint~='phone']").filter(function() {
	      return !/^$/.test($(this).val()) && !/\d{10}/.test($(this).val());
	    });
	    
	    invalidPhones.each(function() {
	      labelNames.push($(this).attr("labelname"));
	      invalidElements.push($(this));
	    });
	    
	    if (invalidPhones.size() > 0) {
	      phoneIsValid = false;
	      phoneInvalidCallback(labelNames, invalidElements); 
	    }
	}());
	
    return tempValid(form) && emailIsValid && phoneIsValid ;
  };
  
  // 設定必填檢查不通過時的callback
  var setEmailInvalidCallback = function(callback) {
    self.emailInvalidCallback = callback;
  };

  var emailInvalidCallback = function(labelNames, invalidElements) {
	
    if ($.isFunction(self.emailInvalidCallback)) {
      self.emailInvalidCallback(labelNames, invalidElements);
    } else { // 預設 window.alert 提示
	  if (invalidElements.size() > 0) {
	    errorMsg("email格式不正確!");
	    return;
	  }
    }
  };

  // 設定必填檢查不通過時的callback
  var setPhoneInvalidCallback = function(callback) {
    self.phoneInvalidCallback = callback;
  };

  var phoneInvalidCallback = function(labelNames, invalidElements) {
	
    if ($.isFunction(self.phoneInvalidCallback)) {
      self.phoneInvalidCallback(labelNames, invalidElements);
    } else { // 預設 window.alert 提示
	  if (invalidElements.size() > 0) {
	    errorMsg("電話必需為10碼數字!");
	    return;
	  }
    }
  };

  window.blockUIExists = false;
  
//設定使用$RS.AJAX發出request時，會顯示loading的動作
  $RS.setAjaxBeforeLoadingCallback(function() {
    
    if ($(".blockUI:first").size() == 0) {
      
      blockUIExists = true;
      $.blockUI({
        message : " ", // 加上這個空白內容才不會顯示預設文字
        css : {
          "border" : "none",
          "background":"none",
          "background-image" : "url(img/loading.gif)",
          "top":  ($(window).height() - 32) /2 + 'px', 
          "left": ($(window).width() - 32) /2 + 'px',
          "width" : "32px",
          "height" : "32px"
        },
        overlayCSS : {background : "none", "z-index" : "2000"}
      });
    }
  });

  //設定使用$RS.AJAX發出request時並且取得response後，會執行的動作
  $RS.setAjaxAfterLoadingCallback(function() {
    
    if (blockUIExists) {
      $.unblockUI();
      blockUIExists = false;
    }
  });

  window.$RS.setRequired = setRequired;
  window.$RS.removeRequired = removeRequired;
  window.$RS.setEmailInvalidCallback = setEmailInvalidCallback;
  window.$RS.setPhoneInvalidCallback = setPhoneInvalidCallback;

}());

