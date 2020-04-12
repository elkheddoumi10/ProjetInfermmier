import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import java.io.FileWriter;
import java.io.File;

public class SolverInfermier {
    public static void main(String[] args) {
        int NbInfermier = 7;
        int NbPost = 6;
        int NbJour = 7;
        //les jour feriers sont represente par les 1
        int[] jf = {0, 0, 1, 0, 0, 1, 1};

// 1. Create a Model
        Model model = new Model("Problem d'infermiers");

// 2. Create variables

        IntVar[][][] X = new IntVar[NbInfermier][NbPost][NbJour];
        IntVar[][] Con = new IntVar[NbInfermier][NbJour];

        for (int i = 0; i < NbInfermier; i++)
            for (int j = 0; j < NbPost; j++)
                for (int t = 0; t < NbJour; t++)
                    X[i][j][t] = model.intVar("X" + (i + 1) + "," + (j + 1) + "," + (t + 1), new int[]{0, 1});

        for (int i = 0; i < NbInfermier; i++)
            for (int t = 0; t < NbJour; t++)
                Con[i][t] = model.intVar("Con" + (i + 1) + (t + 1), new int[]{0, 1});
// 3. Post constraints
        //the first constraint
        // un infermier pour le post 1
        for (int t = 0; t < NbJour; t++) {
            IntVar[] p1 = new IntVar[NbInfermier];
            for (int i = 0; i < NbInfermier; i++) {
                p1[i] = X[i][0][t];
            }
            model.sum(p1, "=", 1).post();
        }
        // un infermier pour le post 6
        for (int t = 0; t < NbJour; t++) {
            IntVar[] p6 = new IntVar[NbInfermier];
            for (int i = 0; i < NbInfermier; i++) {
                p6[i] = X[i][5][t];
            }
            model.sum(p6, "=", 1).post();
        }

        // un infermier pour le post 5
        for (int t = 0; t < NbJour; t++) {
            IntVar[] p5 = new IntVar[NbInfermier];
            for (int i = 0; i < NbInfermier; i++) {
                p5[i] = X[i][4][t];
            }
            model.sum(p5, "=", 2).post();
        }

        //the third constraint : 2=<posts234>=3
        for (int t = 0; t < NbJour; t++) {
            IntVar[] p234 = new IntVar[NbInfermier];
            for (int j = 1; j < 4; j++)
                for (int i = 0; i < NbInfermier; i++)
                    p234[i] = X[i][j][t];
            model.sum(p234, "<=", 3).post();
            model.sum(p234, ">=", 2).post();
        }
        // the fourth constraint

              for (int t = 0; t < NbJour; t++) {
                  if (jf[t] == 0){
                       int tf=t;
                      for (int i = 0; i < NbInfermier; i++) {
                       IntVar[] ov = new IntVar[NbPost+1];
                         for (int j = 0; j < NbPost; j++) {
                                 ov[j] = X[i][j][t];
                            }
                          ov[ov.length-1]=Con[i][tf];
                          model.sum(ov, ">=",1).post();
                }
            }
        }
        //the fifth constrainte : L infirmier(e) qui travaille un jour ferie prend repos les deux jours feries qui suivent.

        for (int i=0;i<NbInfermier;i++){
            //System.out.println("last constrainte");
            int jfr=0;
            for(int t=0;t<NbJour;t++) {
                IntVar[] fer= new IntVar[NbPost];
                if(jf[t]==1){
                    jfr++;
                    for(int j=0;j<NbPost;j++) {
                        fer[j] = X[i][j][t];
                    }
                }
                if(jfr==3) {
                    model.sum(fer,"=",1).post();
                    jfr=0;
                }
            }
        }
// 4. Solve the problem
        model.getSolver().solve();
// 5. Print the solution
        System.out.println("Le Jour       L'infermer       numero de Post");
        for (int t = 0; t < NbJour; t++) {
            String a = "";
            switch (t) {
                case 0:
                    a = "Lundi";
                    break;
                case 1:
                    a = "Mardi";
                    break;
                case 2:
                    a = "Mercredi";
                    break;
                case 3:
                    a = "Jeudi";
                    break;
                case 4:
                    a = "Vendredi";
                    break;
                case 5:
                    a = "Samedi";
                    break;
                case 6:
                    a = "Dimanche";
                    break;
                    default:a="";
            }
            for (int j = 0; j < NbPost; j++)
                for (int i = 0; i < NbInfermier; i++)
                    if (X[i][j][t].getValue() == 1)
                        System.out.println(a + "              " + (i + 1) + "                  " + (j + 1)); // Prints X = 2
        }
        try{
            File ff=new File("resultat.txt"); // définir l'arborescence
            ff.createNewFile();
            FileWriter ffw=new FileWriter(ff);

            ffw.write("Le Jour          L'infermer          numero de Post");  // écrire une ligne dans le fichier resultat.txt
            ffw.write("\n"); // forcer le passage à la ligne
            ffw.write("****************************************************************");
            ffw.write("\n");
            for (int t = 0; t < NbJour; t++) {
                String a = "";
                switch (t) {
                    case 0:
                        a = "Lundi";
                        break;
                    case 1:
                        a = "Mardi";
                        break;
                    case 2:
                        a = "Mercr.";
                        break;
                    case 3:
                        a = "Jeudi";
                        break;
                    case 4:
                        a = "Vendr.";
                        break;
                    case 5:
                        a = "Samedi";
                        break;
                    case 6:
                        a = "Diman.";
                        break;
                    default:a="";
                }
                for (int j = 0; j < NbPost; j++)
                    for (int i = 0; i < NbInfermier; i++)
                        if (X[i][j][t].getValue() == 1) {
                            ffw.write(a + "              " + (i + 1) + "                  " + (j + 1));
                            ffw.write("\n");// Prints X = 2
                        }
            }

            ffw.close(); // fermer le fichier à la fin des traitements
        } catch (Exception e) {}

            for (int i = 0; i < NbInfermier; i++)
                for (int t = 0; t < NbJour; t++) {
                    if (Con[i][t].getValue()==1)
                        System.out.println("C" + (i + 1) + "," + (t + 1) + "=" + Con[i][t].getValue());
                }

    }
}