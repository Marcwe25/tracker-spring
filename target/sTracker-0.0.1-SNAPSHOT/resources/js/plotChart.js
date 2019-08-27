Date.prototype.addDays = function(days) {
    var date = new Date(this.valueOf());
    date.setDate(date.getDate() + days);
    return date;
}


function formatDate(myArray){
	var formattedListArray = [];
    $.each(myArray, function (index, listwithout) {
    	 
    	formattedListArray.push(addDateInList(listwithout));
       });	
    return formattedListArray;  
}

function addDateInList(listWithoutDate){
	var theDate = new Date($('#startDate').val());
	var listWithDate = [];
    $.each(listWithoutDate, function (index, value) {
    	listWithDate.push([theDate.addDays(index),value]);
       });
    return listWithDate;
}

function renderPlot(data) {
	data=formatDate(data);
    $("#chart").empty();
    $("#chartError").hide();
 
    $.jqplot("chart", data, {
        axes: {
            xaxis: {
                renderer:$.jqplot.DateAxisRenderer, 
                rendererOptions:{
                    tickRenderer:$.jqplot.CanvasAxisTickRenderer
                },
                tickOptions:{ 
                    fontSize:'10pt', 
                    fontFamily:'Tahoma', 
                    pad:0,
                    padMin: 0,
                    padMax: 1.2
                }
            },
            yaxis: {
               
            }
        }});
}


 
function renderError() {
    $("#chart").empty();
    $("#chartError").show();
}
 
function reloadPlot(choosen) {
	if(choosen.length>0){
		   $.ajax({
		    	async: false,
		    	type: "POST",
		        url: "plotChart",
		        contentType: 'application/json;charset=utf-8',
		        data: JSON.stringify(choosen),
		        dataType: "json",
		        success: renderPlot,
		        error: renderError
		    });
	} else {
		alert("choose items to show on graph");
	}
 
}

function send(id,date){
	var p = {'id':id,'date':date};
    $.ajax({
    	type: "POST",
        url: "restDateFilter",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(p),
        dataType: "json",
        success: $(id).val(date),
    });
}
 
function initializeCountries() {
	$.jqplot.config.enablePlugins = true;
	$(function(){
		$("#myApply").click(function(){
			var mycheck = [];
			$.each($("input[name='trObject']:checked"),function(){
				mycheck.push($(this).val())
			})
			reloadPlot(mycheck);
		})
	});
	$(function(){
		$('#startDate').change(function(){
			send("#startDate",$('#startDate').val())
		})
	});
	$(function(){
		$('#endDate').change(function(){
			send("#endDate",$('#endDate').val())
		})
	});
}
 
$(document).ready(initializeCountries);