package sercandevops.com.notsepeteekle;

public class DataEvent {

    public static class NotEkleDialogGoster{

        private int tetikle;

        public NotEkleDialogGoster(int tetikle) {
            this.tetikle = tetikle;
        }

        public int getTetikle() {
            return tetikle;
        }

        public void setTetikle(int tetikle) {
            this.tetikle = tetikle;
        }
    }//static inner class

    public static class DialogTamamlaNotPosition{

        private int position;

        public DialogTamamlaNotPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

    }//inncer class

    public static class TamamlanacakNotPosition {

        private int position;
        public TamamlanacakNotPosition(int position) {
            this.position = position;
        }
        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }//INNER

    public static class NotTamamlaPosition {

        private int position;
        public NotTamamlaPosition(int position) {
            this.position = position;
        }
        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }//INNER




}
