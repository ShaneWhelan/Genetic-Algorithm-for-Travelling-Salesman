package travellingSalesman;

public class TownDatabase {

    /* list of 100 towns/cities */
    private static String [] names = {
        "Dublin",
        "Cork",
        "Limerick",
        "Galway",
        "Waterford",
        "Drogheda",
        "Dundalk",
        "Swords",
        "Bray",
        "Navan",
        "Ennis",
        "Kilkenny",
        "Tralee",
        "Carlow",
        "Newbridge",
        "Naas",
        "Athlone",
        "Portlaoise",
        "Mullingar",
        "Wexford",
        "Balbriggan",
        "Letterkenny",
        "Celbridge",
        "Sligo",
        "Clonmel",
        "Greystones",
        "Malahide",
        "Leixlip",
        "Carrigaline",
        "Tullamore",
        "Killarney",
        "Arklow",
        "Maynooth",
        "Cobh",
        "Castlebar",
        "Midleton",
        "Mallow",
        "Ashbourne",
        "Ballina",
        "Laytown-Bettystown-Mornington",
        "Enniscorthy",
        "Wicklow",
        "Tramore",
        "Cavan",
        "Athy",
        "Shannon",
        "Skerries",
        "Longford",
        "Dungarvan",
        "Portmarnock",
        "Rush",
        "Gorey",
        "Ratoath",
        "Nenagh",
        "Trim",
        "Tuam",
        "New Ross",
        "Kildare",
        "Thurles",
        "Youghal",
        "Portarlington",
        "Monaghan",
        "Lusk",
        "Edenderry",
        "Dunboyne",
        "Buncrana",
        "Donabate",
        "Clane",
        "Ballinasloe",
        "Bandon",
        "Fermoy",
        "Newcastle West",
        "Westport",
        "Carrick-on-Suir",
        "Kells",
        "Birr",
        "Kinsealy-Drinan",
        "Passage West",
        "Roscommon",
        "Kilcock",
        "Roscrea",
        "Tipperary",
        "Sallins",
        "Loughrea",
        "Blessington",
        "Ardee",
        "Carrickmacross",
        "Kinsale",
        "Ballybofey-Stranorlar",
        "Listowel",
        "Oranmore",
        "Mountmellick",
        "Clonakilty",
        "Carrigtwohill",
        "Cashel",
        "Kilcoole",
        "Duleek",
        "Carrick-on-Shannon",
        "Tullow",
        "Athenry"};





    /* Grid coordinates for the 100 towns.
     * Based on the 500km x 500km Irish grid reference system.
     * (0km,0km) would be the most South-Westerly point,
     * (500km,500km) the most North-Easterly point.



    /*
     * X-Coordinates (West-East) for the 100 towns.
     */
    private static double [] xCoords = {
        315.8,
        167.5,
        157.5,
        129.5,
        259.6,
        308.8,
        304.8,
        318.4,
        326.4,
        287.2,
        133.3,
        250.6,
        82.8,
        272.4,
        280.1,
        289.3,
        203.3,
        246.5,
        242.5,
        305.1,
        320.0,
        217.1,
        297.1,
        168.5,
        219.9,
        329.7,
        322.5,
        300.3,
        172.7,
        233.5,
        96.9,
        324.0,
        293.5,
        179.3,
        114.6,
        187.9,
        154.9,
        306.0,
        124.0,
        315.6,
        296.9,
        331.2,
        257.6,
        241.9,
        268.0,
        140.2,
        324.8,
        213.5,
        225.9,
        323.8,
        326.3,
        315.1,
        301.6,
        186.5,
        280.0,
        143.6,
        271.5,
        272.6,
        211.8,
        210.2,
        254.0,
        266.8,
        174.6,
        263.3,
        301.0,
        234.6,
        322.5,
        287.2,
        185.4,
        148.8,
        180.8,
        128.2,
        100.4,
        239.8,
        273.8,
        205.8,
        321.2,
        176.7,
        187.9,
        300.3,
        213.2,
        188.9,
        288.8,
        162.1,
        297.6,
        295.8,
        283.7,
        163.7,
        219.4,
        98.8,
        138.6,
        244.9,
        138.1,
        182.0,
        207.5,
        329.3,
        304.8,
        193.5,
        285.2,
        150.0
        };

    /*
     * Y-Coordinates (South-North) for the 100 Irish towns.
     */
    private static double [] yCoords = {
        234.6,
        71.9,
        157.1,
        225.2,
        111.5,
        275.4,
        307.4,
        246.9,
        218.5,
        267.7,
        178.0,
        156.3,
        114.1,
        177.1,
        215.2,
        219.6,
        242.0,
        198.6,
        252.3,
        121.3,
        264.1,
        412.1,
        233.0,
        335.4,
        122.9,
        212.2,
        246.2,
        236.0,
        62.5,
        224.8,
        90.9,
        173.5,
        237.8,
        66.6,
        290.5,
        73.6,
        98.2,
        252.5,
        319.2,
        273.4,
        139.9,
        194.0,
        101.4,
        304.1,
        193.9,
        162.4,
        260.6,
        275.0,
        93.0,
        243.2,
        254.3,
        159.8,
        251.9,
        178.7,
        256.7,
        252.1,
        127.8,
        212.4,
        158.3,
        78.1,
        212.5,
        333.8,
        188.8,
        232.8,
        242.2,
        432.0,
        250.1,
        227.8,
        230.7,
        55.1,
        98.7,
        133.6,
        284.1,
        121.8,
        275.9,
        204.5,
        243.1,
        68.7,
        264.8,
        236.0,
        189.4,
        135.8,
        223.0,
        216.3,
        214.2,
        290.6,
        303.9,
        50.6,
        394.6,
        133.8,
        224.5,
        207.6,
        41.7,
        73.0,
        140.8,
        208.0,
        268.7,
        299.6,
        172.8,
        228.2
        };


    final static int NUM_TOWNS = 100;

    public static int getNumTowns()    {
        return NUM_TOWNS;
    }


    /* Simple index checker. Valid town index is from 1 to 100 inclusive. */
    private static void checkForValidIndex(int town) {
        if (town < 1 || town > NUM_TOWNS) {
            System.err.println("Invalid town index " + town);
            System.exit(1);
        }
    }

    /* getter for town/city name */
    public static String getTownName(int town) {
        checkForValidIndex(town);
        return names[town - 1]; // decrement by 1 (Java arrays start at index 0 and town indices start at 1)
    }

    /* getter for town x-coord */
    public static double getXCoord(int town) {
        checkForValidIndex(town);
        return xCoords[town - 1];
    }

    /* getter for town y-coord */
    public static double getYCoord(int town) {
        checkForValidIndex(town);
        return yCoords[town - 1];
    }

    /* calculates the Euclidean (straight-line) distance in kilometres between the two towns */
    public static double distance(int town1, int town2) {
        double dx, dy;
        checkForValidIndex(town1);
        checkForValidIndex(town2);
        dx = xCoords[town1 - 1] - xCoords[town2 - 1];
        dy = yCoords[town1 - 1] - yCoords[town2 - 1];
        return Math.sqrt(dx*dx + dy*dy);
    }
}
 