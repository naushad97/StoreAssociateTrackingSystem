$(document).ready(function() {

	$.ajax({
		url : "/storeAssociateTracking/getZoneDetails",

		success : function(result) {

			var data = [];

			for (var i = 0; i < result.length; i++) {

				data.push({
					"x" : result[i].zoneName,
					"value" : 15
				});

			}

			// create the chart

			var chart = anychart.pie();

			// set the chart title

			/*chart.title("Live distribution of associates zones across the store");*/

			// add the data
			chart.data(data);

			// display the chart in the container

			chart.container('container');

			chart.draw();

		}

	});

	
	$(document).on("click", ".zone-list", function(){
		viewZonalAllocation($(this));
	})
});

function viewZonalAllocation($this){
	var zoneId = $this.attr("id");
	
	$.ajax({
		url : "/storeAssociateTracking/getAllAssociateLocation/" + zoneId,
		cache:false
	}).done(function(data){
		if(data.locationAndAssociateDetailsList != undefined && data.locationAndAssociateDetailsList.length > 0){
			$.each(data.locationAndAssociateDetailsList, function(i,d){
				
				var htmlcontent = '<tr><th scope="row">'
					+ d[i].associateName + '</th><td>'
					+ d[i].zoneName + '</td><td>'
					+ d[i].time + '</td><td>'
					+ d[i].cpuUsage + '</td></tr>';

			$('#tableBodyHTML').append(htmlcontent);
				
			});
		}else{
			
			$('#tableBodyHTML').append("Either your day has not started or there is no info available");

		}
	}).fail(function(){
		
	})
		
}