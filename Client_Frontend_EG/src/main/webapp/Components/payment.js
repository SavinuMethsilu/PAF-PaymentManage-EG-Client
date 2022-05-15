$(document).ready(function()
{
	 $("#alertSuccess").hide();
 	 $("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
 	$("#alertSuccess").hide();
 	$("#alertError").text("");
 	$("#alertError").hide();

	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true)
	{
		 $("#alertError").text(status);
 		 $("#alertError").show();
 		 return;
 	}
 	
	// If valid-------------------------
 	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
 	{
 		url : "PaymentAPI",
 		type : type,
 		data : $("#formPayment").serialize(),
 		dataType : "text",
 		complete : function(response, status)
 		{
 			onPaymentSaveComplete(response.responseText, status);
 		}
 	}); 
 });

function onPaymentSaveComplete(response, status)
	{
		if (status == "success")
		{
			 var resultSet = JSON.parse(response);
 			 if (resultSet.status.trim() == "success")
			 {
 				$("#alertSuccess").text("Successfully saved.");
 				$("#alertSuccess").show();
 				$("#divPaymentGrid").html(resultSet.data);
 			 } 
 			 else if (resultSet.status.trim() == "error")
			 {
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			 }
 		} 
 		else if (status == "error")
 		{
 			$("#alertError").text("Error while saving.");
 			$("#alertError").show();
 		} 
 		else
 		{
 			$("#alertError").text("Unknown error while saving..");
 			$("#alertError").show();
 		}
		$("#hidPaymentIDSave").val("");
 		$("#formPayment")[0].reset();
}

	// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
	{
		 $("#hidPaymentIDSave").val($(this).data("payID"));
		 $("#customerName").val($(this).closest("tr").find('td:eq(0)').text());
		 $("#amount").val($(this).closest("tr").find('td:eq(1)').text());
 		 $("#cardNumber").val($(this).closest("tr").find('td:eq(2)').text());
 		 
	});
	
	
	
	$(document).on("click", ".btnRemove", function(event)
	{
 		$.ajax(
 		{
 			url : "PaymentAPI",
 			type : "DELETE",
 			data : "payID=" + $(this).data("payID"),
 			dataType : "text",
 			complete : function(response, status)
 			{
 				onPaymentDeleteComplete(response.responseText, status);
 			}
 		});
	});


	function onPaymentDeleteComplete(response, status)
	{
		if (status == "success")
 		{
 			var resultSet = JSON.parse(response);
 			if (resultSet.status.trim() == "success")
 			{
 				$("#alertSuccess").text("Successfully deleted.");
 				$("#alertSuccess").show();
 				$("#divPaymentGrid").html(resultSet.data);
 			} 
 			else if (resultSet.status.trim() == "error")
 			{
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			}
 		} 
 		else if (status == "error")
 		{
 				$("#alertError").text("Error while deleting.");
 				$("#alertError").show();
 		} 
 		else
 		{
 				$("#alertError").text("Unknown error while deleting..");
 				$("#alertError").show();
 		}
}
	

	// CLIENT-MODEL================================================================
	function validatePaymentForm()
	{
		
		// NAME
		if ($("#customerName").val().trim() == "")
 		{
 			return "Insert Customer Name.";
 		}

		// PRICE-------------------------------
		if ($("#amount").val().trim() == "")
 		{
 			return "Insert Customer amount.";
 		}

		// CODE	------------------------
		if ($("#cardNumber").val().trim() == "")
		{
 			return "Insert Customer Card Number.";
 		}
 		

		return true;
	}