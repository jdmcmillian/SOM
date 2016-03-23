

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author James D. McMillian
 */
public class NUTList {

    public static class NutritionalDataEntry {

        public String Shrt_Desc;// AKA the Name of thefood product
        public int NDB_No;
        //
        public double Water_g; // percentage water
        public double Energ_Kcal; // Calories
        public double GmWt_1;
        //
        public double Lipid_Tot_g;// fat
        public double Carbohydrt_g;// sugars
        public double Protein_g;//Proteins
        public double Cholestrl_mg;
        public double Fiber_TD_g;
        //
        public double Sodium_mg;//Salt
        public double Calcium_mg;
        public double Iron_mg;
        public double Potassium_mg;
        //  Known HIGH Values for normalizing
        public static double Water_percent_HIGH; // percentage water
        public static double Energy_Kcal_HIGH; // Calories
        public static double GmWt_1_HIGH;
        //
        public static double Lipid_Tot_g_HIGH;// fat
        public static double Carbohydrt_g_HIGH;// sugars
        public static double Protein_g_HIGH;//Proteins
        public static double Cholestrl_mg_HIGH;
        public static double Fiber_TD_g_HIGH;
        //
        public static double Sodium_mg_HIGH;//Salt
        public static double Calcium_mg_HIGH;
        public static double Iron_mg_HIGH;
        public static double Potassium_mg_HIGH;
        //  Known LOW Values for normalizing
        public static double Water_percent_LOW; // percentage water
        public static double Energy_Kcal_LOW; // Calories
        public static double GmWt_1_LOW;
        //
        public static double Lipid_Tot_g_LOW;// fat
        public static double Carbohydrt_g_LOW;// sugars
        public static double Protein_g_LOW;//Proteins
        public static double Cholestrl_mg_LOW;
        public static double Fiber_TD_g_LOW;
        //
        public static double Sodium_mg_LOW;//Salt
        public static double Calcium_mg_LOW;
        public static double Iron_mg_LOW;
        public static double Potassium_mg_LOW;

        public double getNormalizedCalcium_mg() {
            return Calcium_mg / this.Calcium_mg_HIGH;
        }

        public void setCalcium_mg(double Calcium_mg) {
            this.Calcium_mg = Calcium_mg;
        }

        public static double getCalcium_mg_HIGH() {
            return Calcium_mg_HIGH;
        }

        public static void setCalcium_mg_HIGH(double Calcium_mg_HIGH) {
            NutritionalDataEntry.Calcium_mg_HIGH = Calcium_mg_HIGH;
        }

        public static double getCalcium_mg_LOW() {
            return Calcium_mg_LOW;
        }

        public static void setCalcium_mg_LOW(double Calcium_mg_LOW) {
            NutritionalDataEntry.Calcium_mg_LOW = Calcium_mg_LOW;
        }

        public double getNormalizedCarbohydrt_g() {
            return Carbohydrt_g / this.Carbohydrt_g_HIGH;
        }

        public void setCarbohydrt_g(double Carbohydrt_g) {
            this.Carbohydrt_g = Carbohydrt_g;
        }

        public static double getCarbohydrt_g_HIGH() {
            return Carbohydrt_g_HIGH;
        }

        public static void setCarbohydrt_g_HIGH(double Carbohydrt_g_HIGH) {
            NutritionalDataEntry.Carbohydrt_g_HIGH = Carbohydrt_g_HIGH;
        }

        public static double getCarbohydrt_g_LOW() {
            return Carbohydrt_g_LOW;
        }

        public static void setCarbohydrt_g_LOW(double Carbohydrt_g_LOW) {
            NutritionalDataEntry.Carbohydrt_g_LOW = Carbohydrt_g_LOW;
        }

        public double getNormalizedCholestrl_mg() {
            return Cholestrl_mg / this.Cholestrl_mg_HIGH;
        }

        public void setCholestrl_mg(double Cholestrl_mg) {
            this.Cholestrl_mg = Cholestrl_mg;
        }

        public static double getCholestrl_mg_HIGH() {
            return Cholestrl_mg_HIGH;
        }

        public static void setCholestrl_mg_HIGH(double Cholestrl_mg_HIGH) {
            NutritionalDataEntry.Cholestrl_mg_HIGH = Cholestrl_mg_HIGH;
        }

        public static double getCholestrl_mg_LOW() {
            return Cholestrl_mg_LOW;
        }

        public static void setCholestrl_mg_LOW(double Cholestrl_mg_LOW) {
            NutritionalDataEntry.Cholestrl_mg_LOW = Cholestrl_mg_LOW;
        }

        public double getNormalizedEnergy_Kcal() {
            return Energ_Kcal / this.getEnergy_Kcal_HIGH();
        }

        public void setEnergy_Kcal(double Energy_Kcal) {
            this.Energ_Kcal = Energy_Kcal;
        }

        public static double getEnergy_Kcal_HIGH() {
            return Energy_Kcal_HIGH;
        }

        public static void setEnergy_Kcal_HIGH(double Energy_Kcal_HIGH) {
            NutritionalDataEntry.Energy_Kcal_HIGH = Energy_Kcal_HIGH;
        }

        public static double getEnergy_Kcal_LOW() {
            return Energy_Kcal_LOW;
        }

        public static void setEnergy_Kcal_LOW(double Energy_Kcal_LOW) {
            NutritionalDataEntry.Energy_Kcal_LOW = Energy_Kcal_LOW;
        }

        public double getNormalizedFiber_TD_g() {
            return Fiber_TD_g / this.getFiber_TD_g_HIGH();
        }

        public void setFiber_TD_g(double Fiber_TD_g) {
            this.Fiber_TD_g = Fiber_TD_g;
        }

        public static double getFiber_TD_g_HIGH() {
            return Fiber_TD_g_HIGH;
        }

        public static void setFiber_TD_g_HIGH(double Fiber_TD_g_HIGH) {
            NutritionalDataEntry.Fiber_TD_g_HIGH = Fiber_TD_g_HIGH;
        }

        public static double getFiber_TD_g_LOW() {
            return Fiber_TD_g_LOW;
        }

        public static void setFiber_TD_g_LOW(double Fiber_TD_g_LOW) {
            NutritionalDataEntry.Fiber_TD_g_LOW = Fiber_TD_g_LOW;
        }

        public double getNormalizedGmWt_1() {
            return GmWt_1 / this.getGmWt_1_HIGH();
        }

        public void setGmWt_1(double GmWt_1) {
            this.GmWt_1 = GmWt_1;
        }

        public static double getGmWt_1_HIGH() {
            return GmWt_1_HIGH;
        }

        public static void setGmWt_1_HIGH(double GmWt_1_HIGH) {
            NutritionalDataEntry.GmWt_1_HIGH = GmWt_1_HIGH;
        }

        public static double getGmWt_1_LOW() {
            return GmWt_1_LOW;
        }

        public static void setGmWt_1_LOW(double GmWt_1_LOW) {
            NutritionalDataEntry.GmWt_1_LOW = GmWt_1_LOW;
        }

        public double getNormalizedIron_mg() {
            return Iron_mg / this.getIron_mg_HIGH();
        }

        public void setIron_mg(double Iron_mg) {
            this.Iron_mg = Iron_mg;
        }

        public static double getIron_mg_HIGH() {
            return Iron_mg_HIGH;
        }

        public static void setIron_mg_HIGH(double Iron_mg_HIGH) {
            NutritionalDataEntry.Iron_mg_HIGH = Iron_mg_HIGH;
        }

        public static double getIron_mg_LOW() {
            return Iron_mg_LOW;
        }

        public static void setIron_mg_LOW(double Iron_mg_LOW) {
            NutritionalDataEntry.Iron_mg_LOW = Iron_mg_LOW;
        }

        public double getNormalizedLipid_Tot_g() {
            return Lipid_Tot_g / this.getLipid_Tot_g_HIGH();
        }

        public void setLipid_Tot_g(double Lipid_Tot_g) {
            this.Lipid_Tot_g = Lipid_Tot_g;
        }

        public static double getLipid_Tot_g_HIGH() {
            return Lipid_Tot_g_HIGH;
        }

        public static void setLipid_Tot_g_HIGH(double Lipid_Tot_g_HIGH) {
            NutritionalDataEntry.Lipid_Tot_g_HIGH = Lipid_Tot_g_HIGH;
        }

        public static double getLipid_Tot_g_LOW() {
            return Lipid_Tot_g_LOW;
        }

        public static void setLipid_Tot_g_LOW(double Lipid_Tot_g_LOW) {
            NutritionalDataEntry.Lipid_Tot_g_LOW = Lipid_Tot_g_LOW;
        }

        public int getNDB_No() {
            return NDB_No;
        }

        public void setNDB_No(int NDB_No) {
            this.NDB_No = NDB_No;
        }

        public double getNormalizedPotassium_mg() {
            return Potassium_mg / this.getPotassium_mg_HIGH();
        }

        public void setPotassium_mg(double Potassium_mg) {
            this.Potassium_mg = Potassium_mg;
        }

        public static double getPotassium_mg_HIGH() {
            return Potassium_mg_HIGH;
        }

        public static void setPotassium_mg_HIGH(double Potassium_mg_HIGH) {
            NutritionalDataEntry.Potassium_mg_HIGH = Potassium_mg_HIGH;
        }

        public static double getPotassium_mg_LOW() {
            return Potassium_mg_LOW;
        }

        public static void setPotassium_mg_LOW(double Potassium_mg_LOW) {
            NutritionalDataEntry.Potassium_mg_LOW = Potassium_mg_LOW;
        }

        public double getNormalizedProtein_g() {
            return Protein_g / this.getProtein_g_HIGH();
        }

        public void setProtein_g(double Protein_g) {
            this.Protein_g = Protein_g;
        }

        public static double getProtein_g_HIGH() {
            return Protein_g_HIGH;
        }

        public static void setProtein_g_HIGH(double Protein_g_HIGH) {
            NutritionalDataEntry.Protein_g_HIGH = Protein_g_HIGH;
        }

        public static double getProtein_g_LOW() {
            return Protein_g_LOW;
        }

        public static void setProtein_g_LOW(double Protein_g_LOW) {
            NutritionalDataEntry.Protein_g_LOW = Protein_g_LOW;
        }

        public String getShrt_Desc() {
            return Shrt_Desc;
        }

        public void setShrt_Desc(String Shrt_Desc) {
            this.Shrt_Desc = Shrt_Desc;
        }

        public double getNormalizedSodium_mg() {
            return Sodium_mg / this.getSodium_mg_HIGH();
        }

        public void setSodium_mg(double Sodium_mg) {
            this.Sodium_mg = Sodium_mg;
        }

        public static double getSodium_mg_HIGH() {
            return Sodium_mg_HIGH;
        }

        public static void setSodium_mg_HIGH(double Sodium_mg_HIGH) {
            NutritionalDataEntry.Sodium_mg_HIGH = Sodium_mg_HIGH;
        }

        public static double getSodium_mg_LOW() {
            return Sodium_mg_LOW;
        }

        public static void setSodium_mg_LOW(double Sodium_mg_LOW) {
            NutritionalDataEntry.Sodium_mg_LOW = Sodium_mg_LOW;
        }

        public double getNormalizedWater_percent() {
            return Water_g / getWater_percent_HIGH();
        }

        public void setWater_percent(double Water_percent) {
            this.Water_g = Water_percent;
        }

        public static double getWater_percent_HIGH() {
            return Water_percent_HIGH;
        }

        public static void setWater_percent_HIGH(double Water_percent_HIGH) {
            NutritionalDataEntry.Water_percent_HIGH = Water_percent_HIGH;
        }

        public static double getWater_percent_LOW() {
            return Water_percent_LOW;
        }

        public static void setWater_percent_LOW(double Water_percent_LOW) {
            NutritionalDataEntry.Water_percent_LOW = Water_percent_LOW;
        }

        public double[] getFeatureSet(java.util.Set<selectableFeatures> FeatureSelection) {
//            double pattern[]; = new double[5];
//            pattern[0] = this.Lipid_Tot_g;
//            pattern[1] = this.Carbohydrt_g;
//            pattern[2] = this.Protein_g;
//            pattern[3] = this.Cholestrl_mg;
//            pattern[4] = this.Sodium_mg;
            java.util.LinkedList<Double> featuresList = new java.util.LinkedList<Double>();

            for (selectableFeatures f : FeatureSelection) {
                switch (f) {
                    case Water_g:
                        featuresList.add(this.Water_g);
                        break;
                    case Energ_Kcal:
                        featuresList.add(this.Energ_Kcal);
                        break;
                    case GmWt_1:
                        featuresList.add(this.GmWt_1);
                        break;
                    //
                    case Lipid_Tot_g:
                        featuresList.add(this.Lipid_Tot_g);
                        break;
                    case Carbohydrt_g:
                        featuresList.add(this.Carbohydrt_g);
                        break;
                    case Protein_g:
                        featuresList.add(this.Protein_g);
                        break;
                    case Cholestrl_mg:
                        featuresList.add(this.Cholestrl_mg);
                        break;
                    case Fiber_TD_g:
                        featuresList.add(this.Fiber_TD_g);
                        break;
                    //
                    case Sodium_mg:
                        featuresList.add(this.Sodium_mg);
                        break;
                    case Calcium_mg:
                        featuresList.add(this.Calcium_mg);
                        break;
                    case Iron_mg:
                        featuresList.add(this.Iron_mg);
                        break;
                    case Potassium_mg:
                        featuresList.add(this.Potassium_mg);
                        break;
                }
            }
            double pattern[] = new double[featuresList.size()];

            //for (Double feature : featuresList) {
            for (int i = 0; i < featuresList.size(); i++) {
                pattern[i] = featuresList.get(i);
                i++;
            }

            return pattern;
        }
    }

    private static enum databaseFields {

        NULL,
        //
        Nutrition_Entry,
        //
        NDB_No,
        Shrt_Desc,
        Water_g,
        Energ_Kcal,
        Protein_g,
        Lipid_Tot_g,
        Ash_g,
        Carbohydrt_g,
        Fiber_TD_g,
        Sugar_Tot_g,
        Calcium_mg,
        Iron_mg,
        Magnesium_mg,
        Phosphorus_mg,
        Potassium_mg,
        Sodium_mg,
        Zinc_mg,
        Copper_mg,
        Manganese_mg,
        Selenium_g,
        Vit_C_mg,
        Thiamin_mg,
        Riboflavin_mg,
        Niacin_mg,
        Panto_Acid_mg,
        Vit_B6_mg,
        Folate_Tot_g,
        Folic_Acid_g,
        Food_Folate_g,
        Folate_DFE_g,
        Choline_Tot_mg,
        Vit_B12_g,
        Vit_A_IU,
        Vit_A_RAE,
        Retinol_g,
        Alpha_Carot_g,
        Beta_Carot_g,
        Beta_Crypt_g,
        Lycopene_g,
        Lut_Zea_g,
        Vit_E_mg,
        Vit_D_g,
        ViVit_D_IU,
        Vit_K_g,
        FA_Sat_g,
        FA_Mono_g,
        FA_Poly_g,
        Cholestrl_mg,
        GmWt_1,
        GmWt_Desc1,
        GmWt_2,
        GmWt_Desc2,
        Refuse_Pct
    }

    public static enum selectableFeatures {

        Water_g,
        Energ_Kcal,
        GmWt_1,
        Lipid_Tot_g,
        Carbohydrt_g,
        Protein_g,
        Cholestrl_mg,
        Fiber_TD_g,
        Sodium_mg,
        Calcium_mg,
        Iron_mg,
        Potassium_mg
        /**
         * ALL THE AVAILABLE FEATURES, IF IT WERE TOBE EXPANDED OUT Water_g, Energ_Kcal, Protein_g, Lipid_Tot_g, Ash_g, Carbohydrt_g, Fiber_TD_g, Sugar_Tot_g, Calcium_mg, Iron_mg, Magnesium_mg,
         * Phosphorus_mg, Potassium_mg, Sodium_mg, Zinc_mg, Copper_mg, Manganese_mg, Selenium_g, Vit_C_mg, Thiamin_mg, Riboflavin_mg, Niacin_mg, Panto_Acid_mg, Vit_B6_mg, Folate_Tot_g, Folic_Acid_g,
         * Food_Folate_g, Folate_DFE_g, Choline_Tot_mg, Vit_B12_g, Vit_A_IU, Vit_A_RAE, Retinol_g, Alpha_Carot_g, Beta_Carot_g, Beta_Crypt_g, Lycopene_g, Lut_Zea_g, Vit_E_mg, Vit_D_g, ViVit_D_IU,
         * Vit_K_g, FA_Sat_g, FA_Mono_g, FA_Poly_g, Cholestrl_mg, GmWt_1, GmWt_Desc1, GmWt_2, GmWt_Desc2, Refuse_Pct
         *
         */
    }
    protected java.util.Set<selectableFeatures> mvarFeatureSelection = EnumSet.noneOf(selectableFeatures.class);

    public void setFeatures(Set<selectableFeatures> Features) {
        mvarFeatureSelection = Features;
    }

    public java.util.Set<selectableFeatures> getFeatureSelection() {
        return mvarFeatureSelection;
    }
    private java.util.TreeMap<Integer, NUTList.NutritionalDataEntry> mvarNutTree = new java.util.TreeMap<Integer, NUTList.NutritionalDataEntry>();

    public java.util.TreeMap<Integer, NUTList.NutritionalDataEntry> getNutrientTree() {
        return mvarNutTree;
    }

    public void AddNutritionalData(NUTList.NutritionalDataEntry NUTData) {
        mvarNutTree.put(NUTData.NDB_No, NUTData);
    }

    public void AddNutritionalData(java.util.LinkedList<NUTList.NutritionalDataEntry> NUTDataList) {
        for (NutritionalDataEntry nutritionalDataEntry : NUTDataList) {
            mvarNutTree.put(nutritionalDataEntry.NDB_No, nutritionalDataEntry);
        }
    }

    public void removeNutritionEntry(NUTList.NutritionalDataEntry NUTData) {
        if (mvarNutTree.containsKey(NUTData.NDB_No)) {
            mvarNutTree.remove(NUTData.NDB_No);
        }
    }

    public Integer[] getNutritionKeys() {
        //java.util.Set<Integer> set = mvNutTree.keySet();
        Integer keys[] = mvarNutTree.keySet().toArray(new Integer[mvarNutTree.size()]);
        return keys;
    }

    public NUTList.NutritionalDataEntry getNutritionEntry(int key) {
        return mvarNutTree.get(key);
    }

    public java.util.LinkedList<NUTList.NutritionalDataEntry> getEntriesByKeyWord(String keyword, boolean incomplete) {
        return null;
    }

    public int Size() {
        return mvarNutTree.size();
    }

    public java.util.TreeMap<Integer, NUTList.NutritionalDataEntry> ImportXMLDataset(String filename) {
        final java.util.TreeMap<Integer, NUTList.NutritionalDataEntry> lvNutTree = new java.util.TreeMap<Integer, NUTList.NutritionalDataEntry>();

        /*
         * Complete list of all database fields
         */
        //NDB_No
        //Shrt_Desc
        //Water_g
        //Energ_Kcal
        //Protein_g
        //Lipid_Tot_g
        //Ash_g
        //Carbohydrt_g
        //Fiber_TD_g
        //Sugar_Tot_g
        //Calcium_mg
        //Iron_mg
        //Magnesium_mg
        //Phosphorus_mg
        //Potassium_mg
        //Sodium_mg
        //Zinc_mg
        //Copper_mg
        //Manganese_mg
        //Selenium_g
        //Vit_C_mg
        //Thiamin_mg
        //Riboflavin_mg
        //Niacin_mg
        //Panto_Acid_mg
        //Vit_B6_mg
        //Folate_Tot_g
        //Folic_Acid_g
        //Food_Folate_g
        //Folate_DFE_g
        //Choline_Tot_mg
        //Vit_B12_g
        //Vit_A_IU
        //Vit_A_RAE
        //Retinol_g
        //Alpha_Carot_g
        //Beta_Carot_g
        //Beta_Crypt_g
        //Lycopene_g
        //Lut_Zea_g
        //Vit_E_mg
        //Vit_D_g
        //ViVit_D_IU
        //Vit_K_g
        //FA_Sat_g
        //FA_Mono_g
        //FA_Poly_g
        //Cholestrl_mg
        //GmWt_1
        //GmWt_Desc1
        //GmWt_2
        //GmWt_Desc2
        //Refuse_Pct

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                databaseFields CurrentField = databaseFields.NULL;
                NutritionalDataEntry nutritionalObject;

                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("Nutrition_Entry")) {
                        nutritionalObject = new NutritionalDataEntry();
                        CurrentField = databaseFields.Nutrition_Entry;
                    } else if (qName.equalsIgnoreCase("NDB_No")) {
                        CurrentField = databaseFields.NDB_No;
                    } else if (qName.equalsIgnoreCase("Shrt_Desc")) {
                        CurrentField = databaseFields.Shrt_Desc;
                    } else if (qName.equalsIgnoreCase("Water_g")) {
                        CurrentField = databaseFields.Water_g;
                    } else if (qName.equalsIgnoreCase("Energ_Kcal")) {
                        CurrentField = databaseFields.Energ_Kcal;
                    } else if (qName.equalsIgnoreCase("GmWt_1")) {
                        CurrentField = databaseFields.GmWt_1;
                    } //
                    else if (qName.equalsIgnoreCase("Lipid_Tot_g")) {
                        CurrentField = databaseFields.Lipid_Tot_g;
                    } else if (qName.equalsIgnoreCase("Carbohydrt_g")) {
                        CurrentField = databaseFields.Carbohydrt_g;
                    } else if (qName.equalsIgnoreCase("Protein_g")) {
                        CurrentField = databaseFields.Protein_g;
                    } else if (qName.equalsIgnoreCase("Cholestrl_mg")) {
                        CurrentField = databaseFields.Cholestrl_mg;
                    } else if (qName.equalsIgnoreCase("Fiber_TD_g")) {
                        CurrentField = databaseFields.Fiber_TD_g;
                    } //
                    else if (qName.equalsIgnoreCase("Sodium_mg")) {
                        CurrentField = databaseFields.Sodium_mg;
                    } else if (qName.equalsIgnoreCase("Calcium_mg")) {
                        CurrentField = databaseFields.Calcium_mg;
                    } else if (qName.equalsIgnoreCase("Iron_mg")) {
                        CurrentField = databaseFields.Iron_mg;
                    } else if (qName.equalsIgnoreCase("Potassium_mg")) {
                        CurrentField = databaseFields.Potassium_mg;
                    } else {
                        CurrentField = databaseFields.NULL;
                    }
                }

                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("Nutrition_Entry")) {
                        lvNutTree.put(nutritionalObject.NDB_No, nutritionalObject);
                        nutritionalObject = null;
                    }
                }

                public void characters(char ch[], int start, int length) throws SAXException {
                    try {
                        switch (CurrentField) {
                            case NULL:
                                //  do nothing
                                break;
                            case NDB_No:
                                nutritionalObject.NDB_No = Integer.parseInt(String.copyValueOf(ch, start, length).trim());
                                break;
                            case Shrt_Desc:
                                nutritionalObject.Shrt_Desc = String.copyValueOf(ch, start, length);
                                break;
//                            case Water_g:
//                                nutritionalObject.GmWt_1 = Double.parseDouble(String.copyValueOf(ch, start, length));
//                                nutritionalObject.GmWt_1_HIGH = (nutritionalObject.GmWt_1>nutritionalObject.GmWt_1_HIGH)?nutritionalObject.GmWt_1:nutritionalObject.GmWt_1_HIGH;
//                                break;
                            case Energ_Kcal:
                                nutritionalObject.Energ_Kcal = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.Energ_Kcal = (nutritionalObject.Energ_Kcal > nutritionalObject.Energy_Kcal_HIGH) ? nutritionalObject.Energ_Kcal : nutritionalObject.Energy_Kcal_HIGH;
                                break;
                            case GmWt_1:
                                nutritionalObject.GmWt_1 = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.GmWt_1_HIGH = (nutritionalObject.GmWt_1 > nutritionalObject.GmWt_1_HIGH) ? nutritionalObject.GmWt_1 : nutritionalObject.GmWt_1_HIGH;
                                break;
                            case Lipid_Tot_g:
                                nutritionalObject.Lipid_Tot_g = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.Lipid_Tot_g_HIGH = (nutritionalObject.GmWt_1 > nutritionalObject.Lipid_Tot_g_HIGH) ? nutritionalObject.Lipid_Tot_g : nutritionalObject.Lipid_Tot_g_HIGH;
                                break;
                            case Carbohydrt_g:
                                nutritionalObject.Carbohydrt_g = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.Carbohydrt_g_HIGH = (nutritionalObject.Carbohydrt_g > nutritionalObject.Carbohydrt_g_HIGH) ? nutritionalObject.Carbohydrt_g : nutritionalObject.Carbohydrt_g_HIGH;
                                break;
                            case Protein_g:
                                nutritionalObject.Protein_g = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.Protein_g_HIGH = (nutritionalObject.Protein_g > nutritionalObject.Protein_g_HIGH) ? nutritionalObject.Protein_g : nutritionalObject.Protein_g_HIGH;
                                break;
                            case Cholestrl_mg:
                                nutritionalObject.Cholestrl_mg = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.Cholestrl_mg_HIGH = (nutritionalObject.Cholestrl_mg > nutritionalObject.Cholestrl_mg_HIGH) ? nutritionalObject.Cholestrl_mg : nutritionalObject.Cholestrl_mg_HIGH;
                                break;
                            case Fiber_TD_g:
                                nutritionalObject.Fiber_TD_g = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.Fiber_TD_g_HIGH = (nutritionalObject.Fiber_TD_g > nutritionalObject.Fiber_TD_g_HIGH) ? nutritionalObject.Fiber_TD_g : nutritionalObject.Fiber_TD_g_HIGH;
                                break;
                            case Sodium_mg:
                                nutritionalObject.Sodium_mg = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.Sodium_mg_HIGH = (nutritionalObject.Sodium_mg > nutritionalObject.Sodium_mg_HIGH) ? nutritionalObject.Sodium_mg : nutritionalObject.Sodium_mg_HIGH;
                                break;
                            case Calcium_mg:
                                nutritionalObject.Calcium_mg = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.Calcium_mg_HIGH = (nutritionalObject.Calcium_mg > nutritionalObject.Calcium_mg_HIGH) ? nutritionalObject.Calcium_mg : nutritionalObject.Calcium_mg_HIGH;
                                break;
                            case Iron_mg:
                                nutritionalObject.Iron_mg = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.Iron_mg_HIGH = (nutritionalObject.Iron_mg > nutritionalObject.Iron_mg_HIGH) ? nutritionalObject.Iron_mg : nutritionalObject.Iron_mg_HIGH;
                                break;
                            case Potassium_mg:
                                nutritionalObject.Potassium_mg = Double.parseDouble(String.copyValueOf(ch, start, length));
                                nutritionalObject.Potassium_mg_HIGH = (nutritionalObject.Potassium_mg > nutritionalObject.Potassium_mg_HIGH) ? nutritionalObject.Potassium_mg : nutritionalObject.Potassium_mg_HIGH;
                                break;
                            default:
                                //ignore                            
                                break;
                        }
                        CurrentField = databaseFields.NULL;
                    } catch (Exception e) {
                        System.err.printf("Error Parsing '%s'", String.copyValueOf(ch, start, length));
                    }
                }
            };

            saxParser.parse(filename, handler);
        } catch (Exception e) {
            System.err.printf("Error reading datafile %s\n", filename);
            e.printStackTrace();
            lvNutTree.clear();
        }


        mvarNutTree = lvNutTree;
        return lvNutTree;
    }

    public NUTList subsetLabelContains(String Text, boolean CaseSensitive) {
        NUTList newSubSet = new NUTList();
        NutritionalDataEntry entry;
        for (Integer key : mvarNutTree.keySet()) {
            entry = mvarNutTree.get(key);
            if (CaseSensitive) {
                //  CASE SENSITIVE
                if (entry.Shrt_Desc.contains(Text)) {
                    newSubSet.AddNutritionalData(entry);
                }
            } else {
                //  CASE INSENSITIVE
                if (entry.Shrt_Desc.toLowerCase().contains(Text.toLowerCase())) {
                    newSubSet.AddNutritionalData(entry);
                }
            }

        }
        return newSubSet;
    }

    public static enum equalityComparer {

        NotEqual,
        Equal,
        LessThan,
        LessThanOrEqual,
        GreaterThan,
        GreaterThanOrEqual
    }//end equalityComparer enum

    public static class restriction {

        selectableFeatures mvarFeature;
        equalityComparer mvarComparison;
        double mvarValue;

        public restriction(selectableFeatures vFeature, equalityComparer vComparison, double vValue) {
            mvarFeature = vFeature;
            mvarComparison = vComparison;
            mvarValue = vValue;
        }

        public selectableFeatures getFeature() {
            return mvarFeature;
        }

        public equalityComparer getComparison() {
            return mvarComparison;
        }

        public double getTextFilter() {
            return mvarValue;
        }

        public double getValue() {
            return mvarValue;
        }//end getValue method
    }

    public NUTList subsetLabelContainsAndFeature(String vTextFilter, LinkedList<restriction> vRestrictions, boolean CaseSensitive) {
        NUTList newSubSet = new NUTList();
        NutritionalDataEntry entry;
        double featureValue = 0;
        for (Integer key : mvarNutTree.keySet()) {
            entry = mvarNutTree.get(key);

            Boolean qualified = true;

            for (restriction r : vRestrictions) {
                switch (r.getFeature()) {
                    case Water_g:
                        featureValue = entry.Water_g;
                        break;
                    case Energ_Kcal:
                        featureValue = entry.Energ_Kcal;
                        break;
                    case GmWt_1:
                        featureValue = entry.GmWt_1;
                        break;
                    case Lipid_Tot_g:
                        featureValue = entry.Lipid_Tot_g;
                        break;
                    case Carbohydrt_g:
                        featureValue = entry.Carbohydrt_g;
                        break;
                    case Protein_g:
                        featureValue = entry.Protein_g;
                        break;
                    case Cholestrl_mg:
                        featureValue = entry.Cholestrl_mg;
                        break;
                    case Fiber_TD_g:
                        featureValue = entry.Fiber_TD_g;
                        break;
                    case Sodium_mg:
                        featureValue = entry.Sodium_mg;
                        break;
                    case Calcium_mg:
                        featureValue = entry.Calcium_mg;
                        break;
                    case Iron_mg:
                        featureValue = entry.Iron_mg;
                        break;
                    case Potassium_mg:
                        featureValue = entry.Potassium_mg;
                        break;
                }


                switch (r.getComparison()) {
                    case NotEqual:
                        qualified = (featureValue == r.getValue()) ? false : qualified;
                        break;
                    case Equal:
                        qualified = (featureValue != r.getValue()) ? false : qualified;
                        break;
                    case LessThan:
                        qualified = (featureValue >= r.getValue()) ? false : qualified;
                        break;
                    case LessThanOrEqual:
                        qualified = (featureValue > r.getValue()) ? false : qualified;
                        break;
                    case GreaterThan:
                        qualified = (featureValue <= r.getValue()) ? false : qualified;
                        break;
                    case GreaterThanOrEqual:
                        qualified = (featureValue < r.getValue()) ? false : qualified;
                        break;
                }

            }

            if (qualified) {
                if (vTextFilter.equalsIgnoreCase("*")) {
                    newSubSet.AddNutritionalData(entry);
                } else {
                    if (CaseSensitive) {
                        //  CASE SENSITIVE
                        if (entry.Shrt_Desc.contains(vTextFilter)) {
                            newSubSet.AddNutritionalData(entry);
                        }
                    } else {
                        //  CASE INSENSITIVE
                        if (entry.Shrt_Desc.toLowerCase().contains(vTextFilter.toLowerCase())) {
                            newSubSet.AddNutritionalData(entry);
                        }
                    }
                }
            }
        }
        return newSubSet;
    }
}
