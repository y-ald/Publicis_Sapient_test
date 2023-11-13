//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import static
//public class MainTest {
//org.junit.jupiter.api.Assertions.assertEquals;
//
//    public class LawnmowerTest {
//
//        @Test
//        public void testProcessInstructions() {
//            Lawnmower lawnmower = new Lawnmower(1, 2, 'N');
//            lawnmower.processInstructions("GAGAGAGAA", 5, 5);
//            assertEquals("1 3 N", lawnmower.getPosition());
//        }
//
//        @Test
//        public void testMultipleMowers() {
//            Lawnmower lawnmower1 = new Lawnmower(1, 2, 'N');
//            lawnmower1.processInstructions("GAGAGAGAA", 5, 5);
//            assertEquals("1 3 N", lawnmower1.getPosition());
//
//            Lawnmower lawnmower2 = new Lawnmower(3, 3, 'E');
//            lawnmower2.processInstructions("AADAADADDA", 5, 5);
//            assertEquals("5 1 E", lawnmower2.getPosition());
//        }
//
//        @Test
//        public void testOutOfBounds() {
//            Lawnmower lawnmower = new Lawnmower(5, 5, 'N');
//            lawnmower.processInstructions("A", 5, 5);
//            assertEquals("5 5 N", lawnmower.getPosition());
//        }
//
//        @Test
//        public void testInvalidInstruction() {
//            Lawnmower lawnmower = new Lawnmower(0, 0, 'N');
//            lawnmower.processInstructions("Z", 5, 5); // 'Z' is an invalid instruction
//            assertEquals("0 0 N", lawnmower.getPosition());
//        }
//
//        import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//        class MowerTest {
//
//            @Test
//            void testInitialPositionAndOrientation() {
//                Mower mower = new Mower(2, 3, 'N', 5, 5);
//                assertEquals(2, mower.getX());
//                assertEquals(3, mower.getY());
//                assertEquals('N', mower.getOrientation());
//            }
//
//            @Test
//            void testRotateRight() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                mower.processInstructions("D");
//                assertEquals('E', mower.getOrientation());
//            }
//
//            @Test
//            void testRotateLeft() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                mower.processInstructions("G");
//                assertEquals('W', mower.getOrientation());
//            }
//
//            @Test
//            void testMoveForward() {
//                Mower mower = new Mower(1, 2, 'N', 5, 5);
//                mower.processInstructions("A");
//                assertEquals(3, mower.getY());
//            }
//
//            @Test
//            void testMoveBackward() {
//                Mower mower = new Mower(1, 2, 'N', 5, 5);
//                mower.processInstructions("B");
//                assertEquals(1, mower.getY());
//            }
//
//            @Test
//            void testMoveOutsideMaxY() {
//                Mower mower = new Mower(0, 5, 'N', 5, 5);
//                mower.processInstructions("A");
//                assertEquals(5, mower.getY());
//            }
//
//            @Test
//            void testMoveOutsideMinY() {
//                Mower mower = new Mower(0, 0, 'S', 5, 5);
//                mower.processInstructions("A");
//                assertEquals(0, mower.getY());
//            }
//
//            @Test
//            void testMoveOutsideMaxX() {
//                Mower mower = new Mower(5, 0, 'E', 5, 5);
//                mower.processInstructions("A");
//                assertEquals(5, mower.getX());
//            }
//
//            @Test
//            void testMoveOutsideMinX() {
//                Mower mower = new Mower(0, 0, 'W', 5, 5);
//                mower.processInstructions("A");
//                assertEquals(0, mower.getX());
//            }
//
//            @Test
//            void testInvalidInstruction() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                assertThrows(IllegalArgumentException.class, () -> mower.processInstructions("Z"));
//            }
//        }
//
//        import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//        class MowerTest {
//
//            @Test
//            void testInitialPosition() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                assertEquals(0, mower.getX());
//                assertEquals(0, mower.getY());
//                assertEquals('N', mower.getOrientation());
//            }
//
//            @Test
//            void testRotateRight() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                mower.processInstructions("D");
//                assertEquals('E', mower.getOrientation());
//            }
//
//            @Test
//            void testRotateLeft() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                mower.processInstructions("G");
//                assertEquals('W', mower.getOrientation());
//            }
//
//            @Test
//            void testMoveForward() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                mower.processInstructions("A");
//                assertEquals(0, mower.getX());
//                assertEquals(1, mower.getY());
//            }
//
//            @Test
//            void testMoveBackward() {
//                Mower mower = new Mower(0, 1, 'N', 5, 5);
//                mower.processInstructions("B");
//                assertEquals(0, mower.getX());
//                assertEquals(0, mower.getY());
//            }
//
//            @Test
//            void testOutOfBounds() {
//                Mower mower = new Mower(5, 5, 'N', 5, 5);
//                mower.processInstructions("A");
//                assertEquals(5, mower.getX());
//                assertEquals(5, mower.getY());
//            }
//
//            @Test
//            void testInvalidInstruction() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                assertThrows(IllegalArgumentException.class, () -> mower.processInstructions("Z"));
//            }
//
//            @Test
//            void testMoveBackwardOutOfBounds() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                mower.processInstructions("B");
//                assertEquals(0, mower.getX());
//                assertEquals(0, mower.getY());
//            }
//
//            @Test
//            void testRotateLeftMultipleTimes() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                mower.processInstructions("GGG");
//                assertEquals('E', mower.getOrientation());
//            }
//
//            @Test
//            void testMoveForwardAndBackward() {
//                Mower mower = new Mower(0, 0, 'N', 5, 5);
//                mower.processInstructions("ABA");
//                assertEquals(0, mower.getX());
//                assertEquals(0, mower.getY());
//            }
//        }
//
//
//
//        // Add more tests as needed
//    }
//    @Test
//    @DisplayName("Test MessageService.get()")
//    void justAnExample() {
//
//    }
//}
