(function($) {
  
  var supplierAutocomplete = function(textbox, params) {
    
    $RS.autocomplete(textbox, $.extend(true, {
      url : "SupplierComponent_askSuppliersByKeyword",
      value : function(item) {
        return item.name;
      },
      itemFormat : function(item) {
        return item.name;
      }
    }, params));
  };
  
  var productAutocomplete = function(textbox, params) {
	    
    $RS.autocomplete(textbox, $.extend(true, {
      url : "ProductComponent_askProductsByKeyword",
      value : function(item) {
        return item.name;
      },
      itemFormat : function(item) {
        return item.name;
      }
    }, params));
  };
  
  /**
   * 做DOM物件動態移動的函示庫
   */
  var AnimationUtil = {};
  
  /**
   * css class "content" 的div 物件之間，動態切換工具函示
   * current: 目前顯示的div物件 
   * to: 把currrent物件隱藏後，要顯示的div物件
   */
  var switchPanel = function(current, to, callback) {
    
    var $current = $(current);
    var $to = $(to);
    
    $current.addClass("content-hide");
    $to.removeClass("content-hide").addClass("content-standby").animate({"left": "0px"}, {complete: function() {
      $to.removeClass("content-standby").removeAttr("style");
      if ($.isFunction(callback)) {
        callback();
      }
    }});
  };
  
  AnimationUtil.switchPanel = switchPanel;

  window.$RS.supplierAutocomplete = supplierAutocomplete;
  window.$RS.productAutocomplete = productAutocomplete;
  window.$RS.AnimationUtil = AnimationUtil;

}($));