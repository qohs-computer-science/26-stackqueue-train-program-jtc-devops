public class Train {
    private String name, product, origin, destination;
    private int weight, miles;

    public Train(String name, String product, String origin, String destination, int weight, int miles){
        this.name = name;
        this.product = product;
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.miles = miles;
    }

    public String getName(){
        return name;
    }

    public String getProduct(){
        return product;
    }

    public String getOrigin(){
        return origin;
    }

    public String getDestination(){
        return destination;
    }

    public int getWeight(){
        return weight;
    }

    public int getMiles(){
        return miles;
    }

    public void setMiles(int miles){
        this.miles = miles;
    }

    public String toString(){
        return name + " containing " + product;
    }//end
}//main
