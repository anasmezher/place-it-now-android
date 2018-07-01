<?php
header("Content-Type: application/json;charset=utf-8");
$latFrom= deg2rad($_GET['lat']);
$lonFrom = deg2rad($_GET['longi']);
$user_name = "id1015576_placeitnowapp";
$password = "place311311place";
$database = "id1015576_placeitnow";
$server          = "localhost";
$conn = new mysqli($server , $user_name , $password,$database );
$sql= "SELECT * FROM `places`";
$result=$conn->query($sql); 
 $data = array(); // result variable

$i=0;
 while($row = mysqli_fetch_array($result)){
	 
	 	
	$latTo = deg2rad($row['PlaceLocationLatitude']);//تحويل قيمة الطول من درجات الى راديان
	$lonTo = deg2rad($row['PlaceLocationLongitude']);//تحويل قيمة العرض من درجات الى راديان
	$lonDelta = $lonTo - $lonFrom;//حساب قيمة الفرق (دلتا)
    $a = pow(cos($latTo) * sin($lonDelta), 2) +
	
    pow(cos($latFrom) * sin($latTo) - sin($latFrom) * cos($latTo) * cos($lonDelta), 2);
    $b = sin($latFrom) * sin($latTo) + cos($latFrom) * cos($latTo) * cos($lonDelta);
    $angle = atan2(sqrt($a), $b);  
	$distance= $angle * 6371000;
	if(	$distance<=1000){
	     $data[$i]['ID'] = $row['ID']; // rest similarly 
	     $data[$i]['imagename'] = $row['imagename']; // rest similarly 
		 $data[$i]['ptype'] = $row['Type']; // rest similarly 
	     $data[$i]['pname'] = $row['PlaceName']; // rest similarly 
      $i++; 
	}
  
 }
 	  header('Content-type: application/json'); // display result JSON format
echo json_encode(array(
     'status' => true,
     'names' => $data // this is your data variable
));

?>