function show(x){
	var i;
	for( i = 0 ; i<3 ; i++){
		var rid = 'f'+ i.toString();
		if(x==i){document.getElementById(rid).style.display = "contents";}
		else{document.getElementById(rid).style.display = "none";}
	}
}
