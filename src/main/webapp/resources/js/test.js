$(function(){
	$("#myApply").click(function(){
		var mycheck = [];
		$.each($("input[name='trObject']:checked"),function(){
			mycheck.push($(this).val())
		})
		$("#mytest2").html(mycheck.join(", "))
		replayPlot(mycheck);
		
	})
})

function reloadPlot(choosen) {
	$.post("rest/plotChart",choosen);
//    $.ajax({
//        url: encodeURI("rest/plotChart"),
//        type: "POST",
//    });
}

function replayPlot(choosen) {
    $.ajax({
    		'type': 'POST',
            'url':"plotChart",
            'contentType': 'application/json;charset=utf-8',
            'data': JSON.stringify(choosen),
            'dataType': 'json',
            success: function(data) {

            if (data == 'SUCCESS')
            {
            alert(data);
            }
            else
                {
                alert(data);
                }

            }
        });
}


