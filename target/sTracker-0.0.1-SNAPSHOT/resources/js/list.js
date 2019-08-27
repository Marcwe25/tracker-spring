function update(id) {
	url = contextPath + "form/updateForm?id=" + id;
	window.location.href = url
}

function dlete(id) {
	url = contextPath + "form/delete?id=" + id;
	window.location.href = url
}

function redirect(category) {
	url = contextPath + "view/listCategory?category=" + category;
	window.location.href = url
}

function returnToList(){
	url = contextPath + "view/currentList";
	window.location.href = url
}

function incr(id,num){
	var x = document.getElementById(id).value;
	document.getElementById(id).value = Number(x)+Number(num);
}