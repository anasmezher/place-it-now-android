<?php

$placeID = $_GET['pID'];
$user_name = "id1015576_placeitnowapp";
$password = "place311311place";
$database = "id1015576_placeitnow";
$server          = "localhost";
$conn = new mysqli($server , $user_name , $password,$database );
$sql= "SELECT * FROM `places` where ID=\"".$placeID."\"";
$result=$conn->query($sql);
$rows = array();
$i=0;
while($row = mysqli_fetch_array($result)){

   		 $rows[$i]['imagename'] =  $row['imagename']; // rest similarly 
		 $i++;
 }
$conn2 = new mysqli($server , $user_name , $password,$database );
$sql2= "SELECT * FROM `Images` where 	PlaceID=\"".$placeID."\"";
$result2=$conn2->query($sql2);

while($row2 = mysqli_fetch_array($result2)){

   		 $rows[$i]['imagename'] =  $row2['imagename']; // rest similarly 
		 $i++;
 }
 
 	  header('Content-type: application/json'); // display result JSON format
echo json_encode(array(
     'status' => true,
     'names' => $rows // this is your data variable
));


?>