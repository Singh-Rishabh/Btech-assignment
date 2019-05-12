// A Factory creater class to get the factories by passing the information of the different factories name.
package algo;
public class FactoryProducer {
   public static abstractFactory getFactory(String choice){
   
      if(choice.equalsIgnoreCase("SORT")){
         return new alogFactory();
      }
      return null;
   }
}