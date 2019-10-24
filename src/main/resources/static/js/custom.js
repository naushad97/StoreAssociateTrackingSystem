var zoneOptions = "";
$(document).ready(function() {
	
	showLiveDistribution();

	$(document).on("click", ".zone-list", function(){
		//$(".zone-list").removeClass("selected-zone");
		
		$("li").removeClass("selected-zone");		
		//$(".zone-list:first").addClass("selected-zone").trigger("click");
		$(this).addClass("selected-zone");
		
		showLiveDistribution();
		viewZonalAllocation($(this)); 
	});
	
	$(document).on("click", ".allocate-to", function(){
		allocateTo($(this));
	});
	
	$(document).on("click", ".fa-refresh", function(){		
		
		$("li").removeClass("selected-zone");
		$(this).addClass("selected-zone");
		
		$(".zone-list:first").addClass("selected-zone").trigger("click");
		
		$(".statistics-data").hide();
		$(".live-data").show();
	});
	
	
	$(document).on("click", ".statistics", function(){
		$("li").removeClass("selected-zone");
		$(this).addClass("selected-zone");
		getAllAssociateTrackingData();
		$(".statistics-data").show();
		$(".live-data").hide();
	});
	
	
	$(".zone-list:first").addClass("selected-zone").trigger("click");
});

function showLiveDistribution() {
	
	$(".statistics-data").hide();
	$(".live-data").show();
	
	$(".loader-img").show();
	$(".fa-refresh").hide();
	//zoneOptions = "";
	
	$.ajax({
		url: "/trackingApi/v1/getZoneDetails",
		success: function (result) {
			var isAssocPresent = false;
			var data = [];
			for (var i = 0; i < result.length; i++) {
				if(result[i].associateCount > 0){
					isAssocPresent = true;
				}
				data.push({
					"x": result[i].zoneName,
					"value": result[i].associateCount,
					"crowdCount" : result[i].crowdCount,
				});
				$("li[id="+result[i].zoneId+"]").find(".associate-count").text("("+result[i].associateCount+")");
			}
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
			tooltip.format("Associate Present: <b>{%value}</b><br>Percentage Count:<b>{%yPercentOfTotal}<br><b>Crowd Density</b>:{%crowdCount}");

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
	$(".statistics-data").hide();
	$(".live-data").show();
	
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
	        var crowdCount = "";
	        $.each(data.locationAndAssociateDetailsList, function(i, d) {
	            tr += "<tr>" +
	                "<td>" + d.associateName + "</td>" +
	                "<td>" + d.zoneName + "</td>" +
	                "<td><a href='javascript:void(0)' class='allocate-to' id=" + d.associateAsid + " >Allocate To</a><div>"+getZoneOptions()+"</div></td>" +
	                "</tr>";
	            crowdCount = d.crowdCount;
	        });
	        $("#zonalDetailsTbody").html(tr);
	        $("#crowdDensity").html(crowdCount);
	        $(".zonalDetails").show();
	        
	        $(".loader-img").hide();
			$(".fa-refresh").show();
			
	    } else {
	    	/* tr += "<tr>" +
             "<td>This zone/section is yet to open.</td>" +
             "</tr>";
	    	 
	    	    $("#zonalDetailsTbody").html(tr);
		        $(".zonalDetails").show();*/
		        
		        $(".loader-img").hide();
				$(".fa-refresh").show();
	    }
	}).fail(function() {
	    $(".zonalDetails").hide();
	    
	    $(".loader-img").hide();
		$(".fa-refresh").show();
		
	});
		
}

function getZoneOptions(){
	if(zoneOptions != ""){
		return zoneOptions;
	}
	zoneOptions += "<select class='form-control'><option value=''>--Select Zone--</option>"; 
	$(".zone-list").each(function(){
		zoneOptions += "<option value=" + $(this).attr("id") + ">" +$(this).find("a").text() + "</option>";
	});
	zoneOptions += "</select>";
	
	return zoneOptions;
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

function getAllAssociateTrackingData(){
	$(".loader-img").show();
	$(".fa-refresh").hide();
	
	$.ajax({
	    url: "/trackingApi/v1/getAllAssociateTrackingData",
	    method: 'GET',
		contentType: 'application/json',
	    cache: false
	}).done(function(data) {
		$(".table-container").html("");
		$(".statistics-msg").hide();
		if(data != undefined){
			$.each(data, function(key, value) {
				var table="<table class='table table-striped'><thead><tr><th>Associate Name</th><th>Zone/Section</th><th>In-Time</th><th>Out-Time</th><th>TIme Spent</th></tr></thead><tbody>";
				var tr=""
			    $.each(value, function(i, v){
			    	tr+= "<tr><td>"+v.empName+"</td><td>"+v.zone+"</td><td>"+v.entryTime+"</td><td>"+v.exitTime+"</td><td>"+v.timeSpent+"</td></tr>";
			   });
				
				$(".table-container").append(table+tr+"</tbody></table>");
				$(".table-container").append("<br><br>")
			});
		} else {
			$(".statistics-msg").show();
		}
		$(".loader-img").hide();
		$(".fa-refresh").show();
		
	}).fail(function() {
		$(".table-container").html("");
		$(".statistics-msg").show();
	});
}