<?php
header("Content-Type: application/json;charset=utf-8");
$placeID = $_GET['ID'];
$user_name = "id1015576_placeitnowapp";
$password = "place311311place";
$database = "id1015576_placeitnow";
$server          = "localhost";
$conn = new mysqli($server , $user_name , $password,$database );
$sql= "SELECT * FROM `Images` where `ID`=`$placeID`";
$result=$conn->query($sql);
while($row = mysqli_fetch_array($result)){
	   $myObj->imagename = $row['imagename'];
   
 }
 	  header('Content-type: application/json'); // display result JSON format
echo json_encode(array(
     'status' => true,
     'names' => $myObj // this is your data variable
));


?>