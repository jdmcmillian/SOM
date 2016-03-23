/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import javax.swing.JColorChooser;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Zanza259
 */
public class JFMainGUI extends javax.swing.JFrame implements SOM.UpdateEventListener {

    //instance fields
    private SOM mvarSelfOrganizingMap;
    private NUTList patterns;
    private SOM selfOrganizingMap = null;
    private java.util.Set<NUTList.selectableFeatures> targetFeatures = EnumSet.of(
            NUTList.selectableFeatures.Protein_g,
            NUTList.selectableFeatures.Carbohydrt_g,
            NUTList.selectableFeatures.Lipid_Tot_g,
            NUTList.selectableFeatures.Sodium_mg,
            NUTList.selectableFeatures.Cholestrl_mg,
            NUTList.selectableFeatures.Energ_Kcal);
    //      /* Selected target features for project */
//        NUTList.selectableFeatures.Lipid_Tot_g,
//        NUTList.selectableFeatures.Carbohydrt_g,
//        NUTList.selectableFeatures.Protein_g,
//        NUTList.selectableFeatures.Cholestrl_mg,
//        NUTList.selectableFeatures.Sodium_mg,
    private CustomRenderer cRenderer;

    public JFMainGUI() {
        initComponents();
        initTable();
        initSOM();
    }//end constructor

    public final synchronized void initTable() {

        jtFilter.setBackground(Color.WHITE);
        jtFilter.setForeground(Color.BLACK);
        jtFilter.setGridColor(Color.BLACK);

        cRenderer = new CustomRenderer();
        jtFilter.setDefaultRenderer(String.class, cRenderer);


    }//end initTable method

    public final synchronized void initSOM() {

        this.jtEpoch.setText("0");
        this.jtError.setText("0");
        this.jtIteration.setText("0");

        patterns = new NUTList();
        patterns.ImportXMLDataset("simple-dataset.xml");
        patterns.setFeatures(targetFeatures);

        if (selfOrganizingMap != null) {
            selfOrganizingMap.stopTraining();
            try {
                selfOrganizingMap.join();
            } catch (Exception e) {
            }
        }
        selfOrganizingMap = new SOM(targetFeatures, Rules.SOM_WIDTH, Rules.SOM_HEIGHT);
        selfOrganizingMap.addUpdateListener(this);
        jMapPanel.linkSOM(selfOrganizingMap);
        selfOrganizingMap.setPatterns(patterns);

        //assignMapPointColors(patterns);
        updateOutput();
        //selfOrganizingMap.setPatterns(patterns);

    }//end initSOM method

    public void handleUpdateEvent(SOM.UpdateEvent event) {

        this.jMapPanel.updateMapPoints();

        DecimalFormat df = new DecimalFormat("#.#########");

        if (event.iteration > 0) {
            this.jtIteration.setText(df.format(event.iteration));
        }
        if (event.epoch > 0) {
            this.jtEpoch.setText(df.format(event.epoch));
        }
        if (event.error > 0) {
            this.jtError.setText(df.format(event.error));
        }

    }//end updateDisplayComponents method

    public void updateGridInformation(java.util.LinkedList<JMapPanel.mapPointInformation> MapPoints) {
        // call the SOM in evaluate mode, and plot the grid information
        jMapPanel.setMapPoints(MapPoints);
    }//end updateGridInformation

    public java.util.LinkedList<JMapPanel.mapPointInformation> compileMapPoints(NUTList NutPatternSet, java.awt.Color vColor) {

        Integer[] PatternKeyList = NutPatternSet.getNutritionKeys();
        java.util.LinkedList<JMapPanel.mapPointInformation> allPoints = new java.util.LinkedList<JMapPanel.mapPointInformation>();

        for (Integer key : PatternKeyList) {
            NUTList.NutritionalDataEntry nutinfo = NutPatternSet.getNutritionEntry(key);

            JMapPanel.mapPointInformation mapPoint = jMapPanel.newMapPoint(nutinfo, vColor, vColor);
            allPoints.add(mapPoint);
            //System.out.printf("(%d, %d) %s\n", mapPoint.getCoordinate().x, mapPoint.getCoordinate().y, mapPoint.getLabel());
        }//end for e

        return allPoints;

    }//end compileMapPoints

    public void updateOutput() {
        this.jMapPanel.clearMapPoints();
        for (int i = 0; i < jtFilter.getRowCount(); i++) {
            if (jtFilter.getValueAt(i, 0) != null) {
                if (((String) jtFilter.getValueAt(i, 0)).length() > 0) {

                    patternFilter filter = new patternFilter((String) jtFilter.getValueAt(i, 0));
                    NUTList targetPatterns = patterns.subsetLabelContainsAndFeature(filter.text, filter.restrictions, false);

                    this.jMapPanel.addMapPoints(targetPatterns, cRenderer.getRowColor(i), cRenderer.getAltRowColor(i));
                }
            }

            //end if
        }//end for i

    }//end updateOutput method

    private class patternFilter {

        public String text;
        public LinkedList<NUTList.restriction> restrictions = new LinkedList<NUTList.restriction>();

        public patternFilter(String FilterString) {
            int lBrace = FilterString.indexOf("[");
            int rBrace = FilterString.indexOf("]");
            if (lBrace >= 0 && rBrace > lBrace) {
                //  filter directives exist
                text = FilterString.substring(0, lBrace);
                String directives[] = FilterString.substring(lBrace + 1, rBrace).split(",");
                for (String directive : directives) {
                    boolean validDirective = true;
                    NUTList.selectableFeatures directiveFeature = NUTList.selectableFeatures.Calcium_mg;
                    double directiveQuantitative = 0.0;
                    NUTList.equalityComparer directiveComparator = NUTList.equalityComparer.Equal;
                    //
                    String equation[] = directive.split("==|!=|<=|>=|<|>", 2);
                    if (equation.length == 2) {
                        String dFeature = equation[0];
                        directiveQuantitative = new Double(equation[1].trim());

                        if (FilterString.contains("==")) {
                            directiveComparator = NUTList.equalityComparer.Equal;
                        } else if (FilterString.contains("!=")) {
                            directiveComparator = NUTList.equalityComparer.NotEqual;
                        } else if (FilterString.contains("<=")) {
                            directiveComparator = NUTList.equalityComparer.LessThanOrEqual;
                        } else if (FilterString.contains("=>")) {
                            directiveComparator = NUTList.equalityComparer.GreaterThanOrEqual;
                        } else if (FilterString.contains("<")) {
                            directiveComparator = NUTList.equalityComparer.LessThan;
                        } else if (FilterString.contains(">")) {
                            directiveComparator = NUTList.equalityComparer.GreaterThan;
                        } else {
                            validDirective = false;
                        }

                        if (dFeature.equalsIgnoreCase("Calcium")) {
                            directiveFeature = NUTList.selectableFeatures.Calcium_mg;
                        } else if (dFeature.equalsIgnoreCase("Carbohydrate")) {
                            directiveFeature = NUTList.selectableFeatures.Carbohydrt_g;
                        } else if (dFeature.equalsIgnoreCase("Cholesterol")) {
                            directiveFeature = NUTList.selectableFeatures.Cholestrl_mg;
                        } else if (dFeature.equalsIgnoreCase("Calories")) {
                            directiveFeature = NUTList.selectableFeatures.Energ_Kcal;
                        } else if (dFeature.equalsIgnoreCase("Fiber")) {
                            directiveFeature = NUTList.selectableFeatures.Fiber_TD_g;
                        } else if (dFeature.equalsIgnoreCase("GmWt")) {
                            directiveFeature = NUTList.selectableFeatures.GmWt_1;
                        } else if (dFeature.equalsIgnoreCase("Iron")) {
                            directiveFeature = NUTList.selectableFeatures.Iron_mg;
                        } else if (dFeature.equalsIgnoreCase("Lipid")) {
                            directiveFeature = NUTList.selectableFeatures.Lipid_Tot_g;
                        } else if (dFeature.equalsIgnoreCase("Potassium")) {
                            directiveFeature = NUTList.selectableFeatures.Potassium_mg;
                        } else if (dFeature.equalsIgnoreCase("Protein")) {
                            directiveFeature = NUTList.selectableFeatures.Protein_g;
                        } else if (dFeature.equalsIgnoreCase("Sodium")) {
                            directiveFeature = NUTList.selectableFeatures.Sodium_mg;
                        } else if (dFeature.equalsIgnoreCase("Water")) {
                            directiveFeature = NUTList.selectableFeatures.Water_g;
                        } else {
                            validDirective = false;
                        }
                    }
                    //  Ignore the directive, its an invalid directive, try to compare the whole string
                    if (validDirective) {
                        restrictions.add(new NUTList.restriction(directiveFeature, directiveComparator, directiveQuantitative));
                    }
                }

            } else {
                text = FilterString;
            }

            //LinkedList<NUTList.restriction> restrictions = new LinkedList<NUTList.restriction>();
            //restrictions.add(new NUTList.restriction(NUTList.selectableFeatures.Protein_g, NUTList.equalityComparer.LessThan, 5));
        }

        public LinkedList<NUTList.restriction> getRestrictions() {
            return restrictions;
        }

        public String getText() {
            return text;
        }
    }

    class CustomRenderer extends DefaultTableCellRenderer {

        private Color[] tableColors = new Color[34];
        private Color[] altTableColors = new Color[34];

        public CustomRenderer() {
            super();
            tableColors[0] = ColorList.WHITE;
            tableColors[1] = ColorList.LIME;
            tableColors[2] = ColorList.YELLOWGREEN;
            tableColors[3] = ColorList.RED;
            tableColors[4] = ColorList.LIGHTPINK;
            tableColors[5] = ColorList.ALICEBLUE;
            tableColors[6] = ColorList.AQUA;
            tableColors[7] = ColorList.PERU;
            altTableColors[0] = Color.BLACK;
            altTableColors[1] = Color.BLACK;
            altTableColors[2] = Color.BLACK;
            altTableColors[3] = Color.BLACK;
            altTableColors[4] = Color.BLACK;
            altTableColors[5] = Color.BLACK;
            altTableColors[6] = Color.BLACK;
            altTableColors[7] = Color.BLACK;


        }//end CustomRenderer

        public void setRowColor(int row, Color color) {
            tableColors[row] = color; //not entirely safe
        }//end setRowColor method

        public Color getRowColor(int row) {
            return tableColors[row]; //not entirely safe
        }//end getRowColor method

        public void setAltRowColor(int row, Color color) {
            altTableColors[row] = color; //not entirely safe
        }//end setAltRowColor method

        public Color getAltRowColor(int row) {
            return altTableColors[row]; //not entirely safe
        }//end getAltRowColor method

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            try {
                c.setBackground(this.tableColors[row]);
            } catch (Exception e) {
                // fail silently
            }
            return c;
        }//end getTableCellRenderComponent override
    }//end CustomRenderer class

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePopup = new javax.swing.JPopupMenu();
        jmiChangeColor = new javax.swing.JMenuItem();
        jmiChangeAltColor = new javax.swing.JMenuItem();
        jpMainPanel = new javax.swing.JPanel();
        jpControlsPanel = new javax.swing.JPanel();
        jbStart = new javax.swing.JButton();
        jbReset = new javax.swing.JButton();
        jbStop = new javax.swing.JButton();
        jpInfoPanel = new javax.swing.JPanel();
        jlIterationLabel = new javax.swing.JLabel();
        jlEpochLabel = new javax.swing.JLabel();
        jlErrorLabel = new javax.swing.JLabel();
        jtIteration = new javax.swing.JTextField();
        jtEpoch = new javax.swing.JTextField();
        jtError = new javax.swing.JTextField();
        jMapPanel = new JMapPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaInfoDisplay = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtFilter = new javax.swing.JTable();
        jbUpdate = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtInterval = new javax.swing.JTextField();

        jmiChangeColor.setText("Change Color...");
        jmiChangeColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiChangeColorActionPerformed(evt);
            }
        });
        tablePopup.add(jmiChangeColor);

        jmiChangeAltColor.setText("Change Alt Color...");
        jmiChangeAltColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiChangeAltColorActionPerformed(evt);
            }
        });
        tablePopup.add(jmiChangeAltColor);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Self-Organizing Map");
        setResizable(false);

        jpControlsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Controls"));

        jbStart.setText("Start");
        jbStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStartActionPerformed(evt);
            }
        });

        jbReset.setText("Reset");
        jbReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbResetActionPerformed(evt);
            }
        });

        jbStop.setText("Stop");
        jbStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpControlsPanelLayout = new javax.swing.GroupLayout(jpControlsPanel);
        jpControlsPanel.setLayout(jpControlsPanelLayout);
        jpControlsPanelLayout.setHorizontalGroup(
            jpControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpControlsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbReset, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbStop, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpControlsPanelLayout.setVerticalGroup(
            jpControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpControlsPanelLayout.createSequentialGroup()
                .addGroup(jpControlsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbStart)
                    .addComponent(jbStop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbReset)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Info"));

        jlIterationLabel.setText("Iteration:");

        jlEpochLabel.setText("Epoch:");

        jlErrorLabel.setText("Error:");

        jtIteration.setText("0");
        jtIteration.setEnabled(false);

        jtEpoch.setText("0");
        jtEpoch.setEnabled(false);

        jtError.setText("0");
        jtError.setEnabled(false);

        javax.swing.GroupLayout jpInfoPanelLayout = new javax.swing.GroupLayout(jpInfoPanel);
        jpInfoPanel.setLayout(jpInfoPanelLayout);
        jpInfoPanelLayout.setHorizontalGroup(
            jpInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlIterationLabel)
                    .addComponent(jlEpochLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlErrorLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtIteration, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jtEpoch)
                    .addComponent(jtError))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpInfoPanelLayout.setVerticalGroup(
            jpInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInfoPanelLayout.createSequentialGroup()
                .addGroup(jpInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtIteration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlIterationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtEpoch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlEpochLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlErrorLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMapPanel.setBackground(new java.awt.Color(255, 255, 255));
        jMapPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMapPanel.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jMapPanelMouseWheelMoved(evt);
            }
        });
        jMapPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMapPanelMouseClicked(evt);
            }
        });
        jMapPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jMapPanelMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jMapPanelLayout = new javax.swing.GroupLayout(jMapPanel);
        jMapPanel.setLayout(jMapPanelLayout);
        jMapPanelLayout.setHorizontalGroup(
            jMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );
        jMapPanelLayout.setVerticalGroup(
            jMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jtaInfoDisplay.setColumns(20);
        jtaInfoDisplay.setEditable(false);
        jtaInfoDisplay.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jtaInfoDisplay.setRows(5);
        jScrollPane1.setViewportView(jtaInfoDisplay);

        jtFilter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"* (SHOW EVERYTHING)"},
                {"Babyfood[protein>=0.5]"},
                {"Babyfood[protein<0.5]"},
                {"Beef[protein>=5.0]"},
                {"Beef[protein<5.0]"},
                {"Squid"},
                {"Fish"},
                {"Seed"},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Filter"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jtFilter.setComponentPopupMenu(tablePopup);
        jtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtFilterKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(jtFilter);

        jbUpdate.setText("Update");
        jbUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUpdateActionPerformed(evt);
            }
        });

        jLabel1.setText("Update Interval");

        jtInterval.setText("1000");
        jtInterval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtIntervalActionPerformed(evt);
            }
        });
        jtInterval.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtIntervalKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jpMainPanelLayout = new javax.swing.GroupLayout(jpMainPanel);
        jpMainPanel.setLayout(jpMainPanelLayout);
        jpMainPanelLayout.setHorizontalGroup(
            jpMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpMainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpMainPanelLayout.createSequentialGroup()
                        .addComponent(jbUpdate)
                        .addGap(75, 75, 75)))
                .addComponent(jMapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpControlsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addGroup(jpMainPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtInterval)
                        .addGap(22, 22, 22))))
        );
        jpMainPanelLayout.setVerticalGroup(
            jpMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMainPanelLayout.createSequentialGroup()
                .addComponent(jpInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jpControlsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpMainPanelLayout.createSequentialGroup()
                .addGroup(jpMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpMainPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbUpdate)
                        .addGap(2, 2, 2))
                    .addGroup(jpMainPanelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jMapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbStartActionPerformed
        //System.out.println("button press");
        jbStop.setEnabled(true);
        jbStart.setEnabled(false);
        jbReset.setEnabled(false);
//        selfOrganizingMap.startTraining();
        selfOrganizingMap.start();
    }//GEN-LAST:event_jbStartActionPerformed

    private void jbStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbStopActionPerformed
        jbStart.setEnabled(true);
        jbStop.setEnabled(false);
        jbReset.setEnabled(true);
        selfOrganizingMap.stopTraining();
    }//GEN-LAST:event_jbStopActionPerformed

    private void jbResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbResetActionPerformed
        jbStart.setEnabled(true);
        jbStop.setEnabled(true);
        initSOM();
    }//GEN-LAST:event_jbResetActionPerformed

    private void jmiChangeColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiChangeColorActionPerformed


        Color selectedColor = JColorChooser.showDialog(this, "Pick a Color", Color.GREEN);

        if (selectedColor != null && jtFilter.getSelectedRow() != -1) {
            cRenderer.setRowColor(jtFilter.getSelectedRow(), selectedColor);
        }//end if

    }//GEN-LAST:event_jmiChangeColorActionPerformed

    private void jmiChangeAltColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiChangeAltColorActionPerformed

        Color selectedColor = JColorChooser.showDialog(this, "Pick a Color", Color.GREEN);

        if (selectedColor != null && jtFilter.getSelectedRow() != -1) {
            cRenderer.setAltRowColor(jtFilter.getSelectedRow(), selectedColor);
        }//end if

    }//GEN-LAST:event_jmiChangeAltColorActionPerformed

    private void jMapPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMapPanelMouseMoved
    }//GEN-LAST:event_jMapPanelMouseMoved

    private void jMapPanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jMapPanelMouseWheelMoved

        JScrollBar scrollBar = this.jScrollPane1.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getValue() + evt.getUnitsToScroll() * 5);

    }//GEN-LAST:event_jMapPanelMouseWheelMoved

    private void jbUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUpdateActionPerformed

        Cursor temp = this.getCursor();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        updateOutput();

        this.setCursor(temp);

    }//GEN-LAST:event_jbUpdateActionPerformed

    private void jMapPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMapPanelMouseClicked
        this.jtaInfoDisplay.setText(this.jMapPanel.getFormattedPointInformationAt(evt.getPoint()));
        this.jtaInfoDisplay.updateUI();
        this.jtaInfoDisplay.repaint();
        this.jScrollPane1.getVerticalScrollBar().setValue(0);

    }//GEN-LAST:event_jMapPanelMouseClicked

    private void jtFilterKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtFilterKeyTyped
//
//        int row = jtFilter.getSelectedRow();
//        if (row != -1) {
//            if (((String) jtFilter.getValueAt(row, 0)).length() == 0) {
//                jtFilter.setValueAt(null, row, 0);
//                cRenderer.setRowColor(row, Color.GRAY);
//                cRenderer.setAltRowColor(row, Color.GRAY);
//            }
//        }
//
    }//GEN-LAST:event_jtFilterKeyTyped

    private void jtIntervalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtIntervalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtIntervalActionPerformed

    private void jtIntervalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtIntervalKeyTyped
        if (jtInterval.getText().length() > 0) {
            Rules.UPDATE_INTERVAL = new Integer(jtInterval.getText());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jtIntervalKeyTyped

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
















                }//end if
            }//end for
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFMainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }//end try catch
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JFMainGUI().setVisible(true);
            }
        });

    }//end main method
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private JMapPanel jMapPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbReset;
    private javax.swing.JButton jbStart;
    private javax.swing.JButton jbStop;
    private javax.swing.JButton jbUpdate;
    private javax.swing.JLabel jlEpochLabel;
    private javax.swing.JLabel jlErrorLabel;
    private javax.swing.JLabel jlIterationLabel;
    private javax.swing.JMenuItem jmiChangeAltColor;
    private javax.swing.JMenuItem jmiChangeColor;
    private javax.swing.JPanel jpControlsPanel;
    private javax.swing.JPanel jpInfoPanel;
    private javax.swing.JPanel jpMainPanel;
    private javax.swing.JTextField jtEpoch;
    private javax.swing.JTextField jtError;
    private javax.swing.JTable jtFilter;
    private javax.swing.JTextField jtInterval;
    private javax.swing.JTextField jtIteration;
    private javax.swing.JTextArea jtaInfoDisplay;
    private javax.swing.JPopupMenu tablePopup;
    // End of variables declaration//GEN-END:variables
}//end JFMainGUI class
