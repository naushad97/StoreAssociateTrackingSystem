$(document)
	.ready(
		function () {
			$.ajax({
				url: "/dashboardcount",
				success: function (result) {
					$("#customerCount").html(result.customers);
					$("#projectsCount").html(result.projects);
					$("#vmCount").html(result.virtualMachines);
					$("#instancescount").html(result.activeInstances);
				}
			});





			$("#registerBtn")
				.click(
					function () {

						if ($.trim($("#fullname").val()) == "") {
							alert("Please provide your Fullname");
						} else if ($.trim($("#productsku")
								.val()) == "") {
							alert("Please provide a Product SKU");
						} else {

							var datamodel = {
								fullname: $
									.trim($("#fullname")
										.val()),
								productsku: $.trim($(
									"#productsku").val())
							};
							var requestmodel = JSON
								.stringify(datamodel);

							$.ajax({
									type: "POST",
									url: "/products/register",
									headers: {
										"Content-Type": "application/json"
									},
									data: requestmodel,
									success: function (data) {
										alert("Product is registered successfully");
									},
									error: function (data) {
										alert("Product is not registered successfully");
									}
								});

						}
					});

		});