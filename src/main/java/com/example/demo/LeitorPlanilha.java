package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LeitorPlanilha {
    
    @GetMapping()
        public static void main(String[] args) throws IOException {
          String fileName = JOptionPane.showInputDialog("Insira o caminho completo do arquivo em excel");
          List<Item> listaItem = new ArrayList<Item>();
          Lote lote = new Lote();
          HashMap<String, Item> lote2 = new HashMap<>();
          java.io.File resultado = new java.io.File(fileName.substring(0, fileName.length()-3)+"csv");         
          try {
                 FileInputStream arquivo = new FileInputStream(new File(
                               fileName));

                 HSSFWorkbook workbook = new HSSFWorkbook(arquivo);

                 HSSFSheet sheetItens = workbook.getSheetAt(0);

                 Iterator<Row> rowIterator = sheetItens.iterator();

                 while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                       
                        Iterator<Cell> cellIterator = row.cellIterator();
                        Item item = new Item();
                        while (cellIterator.hasNext()) {
                               Cell cell = cellIterator.next();
                               switch (cell.getColumnIndex()) {  
                                    case 1:
                                        item.setInvoice(cell.getStringCellValue());
                                        break;  
                                    case 2:
                                        int seq =(int) cell.getNumericCellValue();
                                        item.setSequencia(seq);
                                        break;
                                    case 3:
                                        item.setCodigo(cell.getStringCellValue());
                                        break;
                                    case 4:
                                        lote.setNumero(cell.getStringCellValue());                                        
                                        break;
                                    case 5:
                                        lote.setValidade(cell.getDateCellValue());
                                        break;
                                    case 6:
                                        lote.setFabricacao(cell.getDateCellValue());
                                        item.setLote(new Lote(lote.getNumero(),lote.getFabricacao(),lote.getValidade()));
                                        item.setQuantidade(1);
                                        break;
                                }
                           }
                         listaItem.add(item);
                    }
                    arquivo.close();

             } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Arquivo Excel não encontrado!");
             }

             if (listaItem.size() == 0) {
                 System.out.println("Não foram encontrados itens");   
             }
             else {
                 //agrupamento dos produtos por código e posterior agrupamento por data de validade
                String itemAuxiliar, loteAuxiliar = new String();
                 for (Item item : listaItem) {
                    item.setDescricao(item.getLote().getNumero());
                    itemAuxiliar=item.getCodigo()+item.getLote().getValidade() + item.getLote().getFabricacao() + item.getInvoice() + item.getSequencia();                
                    //Verifica se a lista está vazia
                    if (lote2.get(itemAuxiliar)!=null) 
                    { //verifica se o item com mesma data de validade está na lista. Caso positivo, adiciona o lote ao campo descrição;
                        loteAuxiliar = lote2.get(itemAuxiliar).getCodigo()+lote2.get(itemAuxiliar).getLote().getValidade() + lote2.get(itemAuxiliar).getLote().getFabricacao()+ lote2.get(itemAuxiliar).getInvoice()+ lote2.get(itemAuxiliar).getSequencia();
                        if ((loteAuxiliar).equals(itemAuxiliar)) 
                        {
                        
                           
                           if (!(lote2.get(itemAuxiliar).getDescricao().contains(item.getDescricao()))) {  
                              lote2.get(itemAuxiliar).setDescricao(lote2.get(itemAuxiliar).getDescricao() + ", "+ item.getDescricao()); 
                              lote2.get(itemAuxiliar).setQuantidade(1);
                           }
                           else {
                                Integer i = lote2.get(itemAuxiliar).getQuantidade()+1;
                                lote2.get(itemAuxiliar).setQuantidade(i);
                                String descricaoAuxiliar = lote2.get(itemAuxiliar).getDescricao();
                                String auxiliar = item.getDescricao() + " (QTY " +i+")";
                                String aux1 = item.getDescricao() + " (QTY " + (i-1)+")";
                                if (lote2.get(itemAuxiliar).getQuantidade()==2){
                                    descricaoAuxiliar = descricaoAuxiliar.replace(item.getDescricao(), auxiliar);
                                }
                                else{
                                    descricaoAuxiliar = descricaoAuxiliar.replace(aux1, auxiliar);
                                }
                                lote2.get(itemAuxiliar).setDescricao(descricaoAuxiliar);
                             
                           }
                        } 
                    }
                    else {
                        item.setDescricao(item.getDescricao());
                        lote2.put(itemAuxiliar,item);
                    }
                    
                }
        }
        escrever(resultado, lote2);
    }
    public static void escrever (File arq, HashMap<String, Item> itens){
		Set<String> mapa = itens.keySet();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {			
			FileWriter fileWriter= new FileWriter(arq,true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
            String texto;

            for (String chaves : mapa) {       
                if (itens.get(chaves).getCodigo() !=null){
                    printWriter.print(itens.get(chaves).getInvoice()+";");
                    printWriter.print(itens.get(chaves).getCodigo());
			        printWriter.print(";LOT: "+itens.get(chaves).getDescricao());
                    texto = ";LOT: "+itens.get(chaves).getDescricao();
                    if (itens.get(chaves).getLote().getFabricacao()!=null){
			            printWriter.print(";FAB: "+formato.format(itens.get(chaves).getLote().getFabricacao()));
                        texto = texto + " - FAB: "+formato.format(itens.get(chaves).getLote().getFabricacao());
                    }
                    else {printWriter.print(";FAB: ");
                         texto = texto + " - FAB: ";
                }
                    if (itens.get(chaves).getLote().getValidade()!=null){
                        printWriter.print(";EXP: "+formato.format(itens.get(chaves).getLote().getValidade()));
                        texto = texto + " - EXP: "+formato.format(itens.get(chaves).getLote().getValidade());
                    }
                    else{printWriter.print(";EXP: ");
                        texto = texto + " - EXP: ";
                    }
                    printWriter.println(texto);
                }
            }
            printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			System.out.println("erro"+e.toString());
		}	
	}
}
