package com.dilli;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

/**
 This program gives the GUI form for the support ticket
 program
 */
public class supportTicketGUI extends JFrame {

    private JTextField DescriptionTextFiled;
    private JTextField ReporterTextField;
    private JComboBox PriorityComboBox;
    private JList OpenTicketList;
    private JList ResolvedTicketList;
    private JButton AddTicketButton;
    private JButton deleteTicketButton;
    private JButton saveAndQuitButton;
    private JPanel rootPanel;
    private DefaultListModel listModel;
    private DefaultListModel ResolvedTicketModel;
    //LinkedList<Ticket> resolvedTickets = new LinkedList<Ticket>();
    //LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();

    public supportTicketGUI(){
        super("Support Ticket");
        setPreferredSize(new Dimension(700,500));
        setContentPane(rootPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listModel = new DefaultListModel();
        ResolvedTicketModel = new DefaultListModel();
        OpenTicketList.setModel(listModel);
        ResolvedTicketList.setModel(ResolvedTicketModel);
        for(int x = 1; x<=5;x++){
            PriorityComboBox.addItem(Integer.toString(x));
        }

        AddTicketButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
               String description = DescriptionTextFiled.getText();
               description.trim();
               String reporter = ReporterTextField.getText();
               reporter.trim();
               String priority = (String)(PriorityComboBox.getSelectedItem());
               priority.trim();
               Date dateReported = new Date();
               //int p = Integer.parseInt(priority);

               Ticket ticket = new Ticket(description,priority,reporter,dateReported,null,null);
               //ticketQueue.add(ticket);
               if(reporter.length() ==0 && description.length()==0){
                   return;
               }
               listModel.addElement(ticket);
               DescriptionTextFiled.setText("");
               ReporterTextField.setText("");
            }
        });
        deleteTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resolution = JOptionPane.showInputDialog("Enter the resolution");
              Ticket toDelete = (Ticket)OpenTicketList.getSelectedValue();
              String description = toDelete.getDescription();
              String reporter = toDelete.getReporter();
              String priority = toDelete.getPriority();
              Date dateReported = toDelete.getDateReported();
              Date dateResolved = new Date();
              Ticket tickets = new Ticket(description,priority,reporter,dateReported,resolution,dateResolved);
              ResolvedTicketModel.addElement(tickets);

              listModel.removeElement(toDelete);
            }
        });
        saveAndQuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedWriter openTicket = new BufferedWriter(new FileWriter("Open_Tickets.txt", true));
                    BufferedWriter resolvedTicket = new BufferedWriter(new FileWriter("Resolved_Ticket.txt", true));
                    for(int x =0; x<listModel.getSize();x++){
                        Ticket ticketToSave = (Ticket)listModel.getElementAt(x);
                        openTicket.append(ticketToSave.toString()+"\n");
                    }
                    if(ResolvedTicketModel.getSize()!=0) {
                        for (int x = 0; x < ResolvedTicketModel.getSize(); x++) {
                            Ticket resolvedTicketToSave = (Ticket) ResolvedTicketModel.getElementAt(x);
                            resolvedTicket.append(resolvedTicketToSave.toString() + "\n");
                        }
                    }
                    openTicket.close();
                    resolvedTicket.close();
                }catch (IOException ie){
                    //TO DO! find out the way to show a message dialogbox.
                }
                System.exit(0);
            }
        });
    }
}
