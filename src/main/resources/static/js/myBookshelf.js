$(function(){

	//切换选项卡，并根据对应选型卡显示按钮
	$(".card-btn").find("span").on("click",function(){
		var title = $(this).attr("title");
		var name = $(this).attr("id");
		var showName = name.split("-")[0];
		$(this).addClass("active").siblings("span").removeClass("active");
		if(showName === "ask"){
			$(this).parent().find("#"+"upload-icon").show();
			$(this).parent().find("#"+"delete-ask").show();
			$(this).parent().find("#"+"ask-upload").show();
			$(this).parent().find("#"+"delete-book").hide();
			$(this).parent().find("#"+"sell-upload").hide();
			$("#container").find("#"+title).show().siblings("div").hide();
		}else if(showName === "sell"){
			$(this).parent().find("#"+"upload-icon").show();
			$(this).parent().find("#"+"delete-book").show();
			$(this).parent().find("#"+"sell-upload").show();
			$(this).parent().find("#"+"ask-upload").hide();
			$(this).parent().find("#"+"delete-ask").hide();
			$("#container").find("#"+title).show().siblings("div").hide();
		}
	});

});
