

function allchecked(obj){
  var prods = document.getElementsByName("prodC");
  var len = prods.length;
  for ( var i = 0; i < len; i++) {
	 prods[i].checked = obj.checked;
  }
}

function inverse(){
  var prods = document.getElementsByName("prodC");
  var len = prods.length;
  for ( var i = 0; i < len; i++) {
	 prods[i].checked = !prods[i].checked;
  }
  //反选检查
  var all = document.getElementsByName("checkall")[0];
  all.checked = true;
  for ( var i = 0; i < len; i++) {
     if(prods[i].checked==false){
       all.checked = false;
     }
  }
}

function chooseOne(){
  var all = document.getElementsByName("checkall")[0];
  var prods = document.getElementsByName("prodC");
  var len = prods.length;
  all.checked = true;
  for ( var i = 0; i < len; i++) {
     if(prods[i].checked==false){
       all.checked = false;
     }
  }
}



function delChecked(){
	var prods = document.getElementsByName("prodC");
    var len = prods.length;
    for ( var i = 0; i < len; i++) {
		if(prods[i].checked==true){
		   alert();
		}
	}
}