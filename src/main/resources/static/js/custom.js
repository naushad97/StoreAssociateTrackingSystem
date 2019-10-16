var zoneOptions = "";
$(document).ready(function() {
	
	showLiveDistribution();

	$(document).on("click", ".zone-list", function(){
		$(".zone-list").removeClass("selected-zone");
		$(this).addClass("selected-zone");
		zoneOptions = "";
		showLiveDistribution();
		viewZonalAllocation($(this)); 
	});
	
	$(document).on("click", ".allocate-to", function(){
		allocateTo($(this));
	});
	
	$(document).on("click", ".fa-refresh", function(){
		$(".zone-list:first").addClass("selected-zone").trigger("click");
	});
	
	$(".zone-list:first").addClass("selected-zone").trigger("click");
});

function showLiveDistribution() {
	$(".loader-img").show();
	$(".fa-refresh").hide();
	zoneOptions = "";
	
	$.ajax({
		url: "/trackingApi/v1/getZoneDetails",
		success: function (result) {
			var isAssocPresent = false;
			var data = [];
			zoneOptions += "<select class='form-control'><option value=''>--Select Zone--</option>";
			for (var i = 0; i < result.length; i++) {
				if(result[i].associateCount > 0){
					isAssocPresent = true;
				}
				data.push({
					"x": result[i].zoneName,
					"value": result[i].associateCount
				});
				zoneOptions += "<option value=" + result[i].zoneId + ">" + result[i].zoneName + "</option>";
				$("li[id="+result[i].zoneId+"]").find(".associate-count").text("("+result[i].associateCount+")");
			}
			zoneOptions += "</select>";

			if (!isAssocPresent) {
				
				$(".loader-img").hide();
				$(".fa-refresh").show();
				
				zoneOptions = "";
				$(".dashboard-chart").hide();
				$(".no-data").show();
				return;
			}

			$(".dashboard-chart").html("").show();
			$(".no-data").hide();

			// create the chart
			var chart = anychart.pie();

			// add the data
			chart.data(data);
			chart.tooltip().useHtml(true);

			// tooltip settings
			var tooltip = chart.tooltip();
			//tooltip.positionMode("point");
			tooltip.format("Associate Present: <b>{%value}</b><br>Percentage Count:<b>{%yPercentOfTotal}%");

			chart.container('container');
			chart.draw();
			
			$(".loader-img").hide();
			$(".fa-refresh").show();
			
		},
		error : function(){
			$(".loader-img").hide();
			$(".fa-refresh").show();
			
			$(".dashboard-chart").hide();
			$(".no-data").text("No Data found.").show();
		}
	});
	
}

function viewZonalAllocation($this){
	
	$(".loader-img").show();
	$(".fa-refresh").hide();
	
	var zoneId = $this.attr("id");
	$.ajax({
	    url: "/trackingApi/v1/getAllAssociateLocation/" + zoneId,
	    cache: false,
	}).done(function(data) {
		
		$(".zonalDetails").hide();
	    $("#zonalDetailsTbody").html("");
	    if (data.locationAndAssociateDetailsList != undefined && data.locationAndAssociateDetailsList.length > 0) {
	        var tr = "";
	        $.each(data.locationAndAssociateDetailsList, function(i, d) {
	            tr += "<tr>" +
	                "<td>" + d.associateName + "</td>" +
	                "<td>" + d.zoneName + "</td>" +
	                "<td><a href='javascript:void(0)' class='allocate-to' id=" + d.associateAsid + " >Allocate To</a><div>"+zoneOptions+"</div></td>" +
	                "</tr>";
	        });
	        $("#zonalDetailsTbody").html(tr);
	        $(".zonalDetails").show();
	        
	        $(".loader-img").hide();
			$(".fa-refresh").show();
	    }
	}).fail(function() {
	    $(".zonalDetails").hide();
	    
	    $(".loader-img").hide();
		$(".fa-refresh").show();
		
	});
		
}

function allocateTo($this){
	var asocId = $this.attr("id");
	var zoneId = $this.parent().find(".form-control").val();
	var data = {"relocateAssociateReqs":[{"associateId":asocId, "toZoneId":zoneId}]};
	//var data = "{\"relocateAssociateReqs\":[{\"associateId\":\"" + asocId + "\", \"toZoneId\":\"" + zoneId + "\"}]}"
	$.ajax({
	    url: "/trackingApi/v1/notification/",
	    method: 'POST',
		data: JSON.stringify(data),
		dataType: 'json',
		contentType: 'application/json',
	    cache: false
	}).done(function(data) {
		if(data.status == 1){
			$(".notify-msg").text(data.message).show().delay(5000).fadeOut();
		} else {
			$(".notify-msg").text("Could not notified").show().delay(5000).fadeOut();
		}
	}).fail(function() {
		$(".notify-msg").text("Notification failed").show().delay(5000).fadeOut();
	});
		
}