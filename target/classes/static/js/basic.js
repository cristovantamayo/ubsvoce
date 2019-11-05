$(document).ready(function(e) {

	$.toForm = function($url, $params, $method, $target, $dump, $callback){
		$("#frm").html("");
		if(undefined===$method || "get"!==$method.toLowerCase()){ $('<input type="hidden" name="_token" value="'+$('meta[name="csrf-token"]').attr('content')+'" />').appendTo("#frm"); }
		for(var item in $params) {
			$('<input type="hidden" name="'+item+'" value="'+$params[item]+'" />').appendTo("#frm");
		}
		if(undefined!==$dump){ alert($("#frm").html()); }
		$("#frm").attr({"target": undefined!==$target ? $target : "_self"});
		
		return $("#frm").attr({"method":undefined===$method || "get"!==$method.toLowerCase() ? "post" : "get", "action":$url}).submit();
		//if(undefined!==$callback){ /*$("#frm").on("submit", this, function(){ $callback(); });*/ }
	};
	
	$.ajaxSetup({
		headers: {
			'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
		}
	})
	
	$.touch=function($url, $params, $callback, $dump, $integral){

		$("#touchloader").stop(true, true).fadeIn("fast");

		if($dump==null && $dump==undefined) {

			$.getJSON($url, $params, function($data){
				//alert($data);

				var $_data = $integral!=undefined && $integral!=null ? $data : $.base64.atob($data['conteudo']);
				$callback($_data);
				
				$("#touchloader").stop(true, true).fadeOut("slow");

			}).fail(function(e){
				console.log('url: '+$url+' | error: ' + e.status);
				console.log(dump($params));
			}).done(function(e){
				//alert("sucesso!");
			}).always(function(e){
				//alert("completo!");
			});

		} else {
			
			console.log($url+'\n\n'+dump($params));
			
		}
	};
	
	$.touchp=function($url, $params, $callback, $dump, $integral){

		$("#touchloader").stop(true, true).fadeIn("fast");

		if($dump==null && $dump==undefined) {

			$.post($url, $params, function($data){
				//alert($data);

				var $_data = $integral!=undefined && $integral!=null ? $data : $.base64.atob($data['conteudo']);
				$callback($_data);
				
				$("#touchloader").stop(true, true).fadeOut("slow");

			}).fail(function(e){
				alert('url: '+$url+' | error: ' + e.status);
				alert(dump($params));
			}).done(function(e){
				//alert("sucesso!");
			}).always(function(e){
				//alert("completo!");
			});

		} else {
			
			alert($url+'\n\n'+dump($params));
			
		}
	};
	
	// 
		
	$.padIt = function($val, $bit, $size){
		var $retorno = $val;
		var $_size = String($val).length;
		if(parseInt($size)>String($val).length){
			$retorno = "";
			$size = $size - $_size;
			for(var $i=0; $i<$size; $i++){
				$retorno+=String($bit);
			}
			$retorno+=String($val);
		}
		return $retorno;
	};
	
});

var trimAll = function(sString){
	sString = new String(sString);
	while (sString.substring(0,1) == " "){
		sString = sString.substring(1, sString.length);
	}
	while (sString.substring(sString.length-1, sString.length) == " "){
		sString = sString.substring(0,sString.length-1);
	}
	return sString;
};



function dump(arr,level) {
	var dumped_text = "";
	if(!level) level = 0;
	
	//The padding given at the beginning of the line.
	var level_padding = "";
	for(var j=0;j<level+1;j++) level_padding += "    ";
	
	if(typeof(arr) == 'object') { //Array/Hashes/Objects 
		for(var item in arr) {
			var value = arr[item];
			
			if(typeof(value) == 'object') { //If it is an array,
				dumped_text += level_padding + "'" + item + "' ...\n";
				dumped_text += dump(value,level+1);
			} else {
				dumped_text += level_padding + "'" + item + "' => \"" + value + "\"\n";
			}
		}
	} else { //Stings/Chars/Numbers etc.
		dumped_text = "===>"+arr+"<===("+typeof(arr)+")";
	}
	return dumped_text;
}

function getDocHeight() {
    var D = document;
    return Math.max(
        D.body.scrollHeight, D.documentElement.scrollHeight,
        D.body.offsetHeight, D.documentElement.offsetHeight,
        D.body.clientHeight, D.documentElement.clientHeight
    );
}

function getViewport() {

	 var viewPortWidth;
	 var viewPortHeight;
	
	 // the more standards compliant browsers (mozilla/netscape/opera/IE7) use window.innerWidth and window.innerHeight
	 if (typeof window.innerWidth != 'undefined') {
	   viewPortWidth = window.innerWidth,
	   viewPortHeight = window.innerHeight
	 }
	
	// IE6 in standards compliant mode (i.e. with a valid doctype as the first line in the document)
	 else if (typeof document.documentElement != 'undefined'
	 && typeof document.documentElement.clientWidth !=
	 'undefined' && document.documentElement.clientWidth != 0) {
		viewPortWidth = document.documentElement.clientWidth,
		viewPortHeight = document.documentElement.clientHeight
	 }
	
	 // older versions of IE
	 else {
	   viewPortWidth = document.getElementsByTagName('body')[0].clientWidth,
	   viewPortHeight = document.getElementsByTagName('body')[0].clientHeight
	 }
	 return [viewPortWidth, viewPortHeight];
}