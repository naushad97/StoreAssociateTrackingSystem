$(document).ready(function() {
	
//	/drawPieChart();
	/*$.ajax({
		url : "/dashboardcount",
		success : function(result) {
			$("#customerCount").html(result.customers);
			$("#projectsCount").html(result.projects);
			$("#vmCount").html(result.virtualMachines);
			$("#instancescount").html(result.activeInstances);
		}
	});*/

	/*$("#registerBtn").click(function() {

		if ($.trim($("#fullname").val()) == "") {
			alert("Please provide your Fullname");
		} else if ($.trim($("#productsku").val()) == "") {
			alert("Please provide a Product SKU");
		} else {

			var datamodel = {
				fullname : $.trim($("#fullname").val()),
				productsku : $.trim($("#productsku").val())
			};
			var requestmodel = JSON.stringify(datamodel);

			$.ajax({
				type : "POST",
				url : "/products/register",
				headers : {
					"Content-Type" : "application/json"
				},
				data : requestmodel,
				success : function(data) {
					alert("Product is registered successfully");
				},
				error : function(data) {
					alert("Product is not registered successfully");
				}
			});

		}
	});
	
	$("#loginBtn").click(function() {
		
	});*/

});

var drawPieChart = function drawPieChart(){
	 $.ajax({
	        url: "/storeAssociateTracking/getZoneDetails",
	        success: function(a) {
	            $("#instancesHTML").html('<strong class="d-block dashtext-1">' + a.total + '</strong><span class="d-block">' + a.month + '</span><small class="d-block"><div>' + a.count + " Instances</div></small>");
	            var b = $("#visitPieChart");
	            //var b = document.getElementById('#visitPieChart').getContext('2d');
	            var c = new Chart(b, {
	                type: "pie",
	                options: {
	                    legend: {
	                        display: false
	                    }
	                },
	                data: {
	                    labels: a.zoneName,
	                    datasets: [ {
	                        data: a.associateCount,
	                        borderWidth: 0,
	                        backgroundColor: [ "#723ac3", "#864DD9", "#9762e6", "#a678eb", "#fff" ],
	                        hoverBackgroundColor: [ "#723ac3", "#864DD9", "#9762e6", "#a678eb", "#a678eb" ]
	                    } ]
	                }
	            });
	        }
	    });
}