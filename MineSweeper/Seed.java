/**
 * Enumeration for the seeds and cell contents
 */
public enum Seed {
   MINE(-1){
      public Seed increase() { return MINE;}
   },
   EMPTY(0){
      public Seed increase() { return ONE; }   // each instance provides its implementation to abstract method
   },
   ONE(1){
      public Seed increase() { return TWO; }
   },
   TWO(2){
      public Seed increase() { return THREE; }
   },
   THREE(3){
      public Seed increase() { return FOUR; }
   },
   FOUR(4){
      public Seed increase() { return FIVE; }
   },
   FIVE(5){
      public Seed increase() { return SIX; }
   },
   SIX(6){
      public Seed increase() { return SEVEN; }
   },
   SEVEN(7){
      public Seed increase() { return EIGHT; }
   },
   EIGHT(8){
      public Seed increase() { return EIGHT; }
   }; // Named constants

   private final int value;      // Private variable

   public abstract Seed increase();

   Seed(int value) {     // Constructor
      this.value = value;
   }

   int getValue() {              // Getter
      return value;
   }
}