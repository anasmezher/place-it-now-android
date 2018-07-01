<?php
header("Content-Type: application/json;charset=utf-8");
$latFrom               = deg2rad($_GET['lat']);
$lonFrom               = deg2rad($_GET['longi']);
$ID                    = $_GET['ID'];
$user_name             = "id1015576_placeitnowapp";
$password              = "place311311place";
$database              = "id1015576_placeitnow";
$server          = "localhost";
$conn = new mysqli($server , $user_name , $password,$database );
$conn2 = new mysqli($server , $user_name , $password,$database );
$conn3 = new mysqli($server , $user_name , $password,$database );
$conn4 = new mysqli($server , $user_name , $password,$database );
$sql                   = "SELECT * FROM `places`";
$result                = $conn->query($sql);
$first_factor_distance = array();
$second_factor_type    = array();
$third_factor_comments = array();
while ($row = mysqli_fetch_array($result)) {
    
    $latTo                   = deg2rad($row['PlaceLocationLatitude']);
    $lonTo                   = deg2rad($row['PlaceLocationLongitude']);
    $placeIDD                = $row['ID'];
    $placeType               = $row['Type'];
    $lonDelta                = $lonTo - $lonFrom;
    $a                       = pow(cos($latTo) * sin($lonDelta), 2) + pow(cos($latFrom) * sin($latTo) - sin($latFrom) * cos($latTo) * cos($lonDelta), 2);
    $b                       = sin($latFrom) * sin($latTo) + cos($latFrom) * cos($latTo) * cos($lonDelta);
    $angle                   = atan2(sqrt($a), $b);
    $distance                = $angle * 6371000;
    $first_factor_distance[] = $distance;
    $typeSql                 = "SELECT COUNT(`Type`) AS `typecount` FROM `Visits` where Type=\"".$placeType."\"";
    $result2                 = $conn2->query($typeSql);
			while ($row2 = mysqli_fetch_array($result2)) {

      $second_factor_type[] = $row2['typecount'];
    }
    $commentSql = "SELECT COUNT(`PlaceID`) AS `commentcount` FROM `Comments` where PlaceID=\"".$placeIDD."\"";
    $result3    = $conn3->query($commentSql);
  		while ($row3 = mysqli_fetch_array($result3)) {

        $third_factor_comments[] = $row3['commentcount'];
    }
    
}

$resultttt = count($third_factor_comments);
for ($x = 0; $x <= $resultttt; $x++) {
    $resultarraytosort[$x] = ($second_factor_type[$x] + $third_factor_comments[$x]) -( $first_factor_distance[$x]);
}
$thisval   = 0;
$datacount = 0;
$resultttt2 = count($resultarraytosort);
for ($x = 0; $x <= $resultttt2; $x++) {
	$thisval = $resultarraytosort[$x];
    for ($y = 0; $y <= $resultttt2; $y++) {
    $innerval = $resultarraytosort[$y];
	    if ($thisval < $innerval) {
            $resultfunction[$x]      = $innerval;
            $resultfunction[$y] = $thisval;
            $IDSArray[$x]                 = $y;
        } else   if ($thisval > $innerval){
            $IDSArray[$x] = $x;
        }
             }
          }
$datacount=count($resultfunction);
$counter = 0;
$conn44 = new mysqli($server , $user_name , $password,$database );
 $data = array(); // result variable

$i=0;
while ($counter < 20 && $counter < $datacount) {
    $placeIdtofind  = $IDSArray[$counter];
    $sqllast        = "SELECT * FROM `places` where ID=\"".$placeIdtofind."\"";
    $resultlast     = $conn44->query($sqllast);
		while ($row66 = mysqli_fetch_array($resultlast)) {

		 $data[$i]['ID'] = $row66['ID']; // rest similarly 
	     $data[$i]['imagename'] = $row66['imagename']; // rest similarly 
		  $data[$i]['ptype'] = $row66['Type']; // rest similarly 
		
	     $data[$i]['pname'] = $row66['PlaceName']; // rest similarly 
         $i++; 
    }

   $counter++; 
}
 	  header('Content-type: application/json'); // display result JSON format
echo json_encode(array(
     'status' => true,
     'names' => $data // this is your data variable
));

?>