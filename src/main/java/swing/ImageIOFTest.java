package swing;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class ImageIOFTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var frame=new ImageIOFrame();
            frame.setTitle("ImageIOFTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class ImageIOFrame extends JFrame{
    private static final int DEFAULT_WIDTH=400;
    private static final int DEFAULT_HEIGHT=400;
    private static Set<String>writerFormats=getWriterFormats();
    private BufferedImage[] images;

    public ImageIOFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        var fileMenu=new JMenu("File");
        var openItem=new JMenuItem("Open");
        openItem.addActionListener(event->openFile());
        fileMenu.add(openItem);

        var saveMenu=new JMenu("Save");
        fileMenu.add(saveMenu);
        Iterator<String>iter=writerFormats.iterator();
        while (iter.hasNext()){
            final String formatName=iter.next();
            var formatItem=new JMenuItem(formatName);
            saveMenu.add(formatItem);
            formatItem.addActionListener(event->
                    saveFile(formatName));
        }

        var exitItem=new JMenuItem("Exit");
        exitItem.addActionListener(event->System.exit(0));
        fileMenu.add(exitItem);

        var menuBar=new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Открыть файл и загрузить изображения
     */
    public void openFile(){
        var chooser=new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        String[] extensions= ImageIO.getReaderFileSuffixes();
        chooser.setFileFilter(new FileNameExtensionFilter("Image files",extensions));
        int r=chooser.showOpenDialog(this);
        if (r!=JFileChooser.APPROVE_OPTION) return;
        File f=chooser.getSelectedFile();
        Box box=Box.createVerticalBox();
        try{
            String name=f.getName();
            String suffix=name.substring(name.lastIndexOf('.')+1);
            Iterator<ImageReader>iter=ImageIO.getImageReadersBySuffix(suffix);
            ImageReader reader=iter.next();
            ImageInputStream imageIn=ImageIO.createImageInputStream(f);
            reader.setInput(imageIn);
            int count=reader.getNumImages(true);
            images=new BufferedImage[count];
            for (int i = 0; i < count; i++) {
                images[i]=reader.read(i);
                box.add(new JLabel(new ImageIcon(images[i])));
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(this,e);
        }
        setContentPane(new JScrollPane(box));
        validate();
    }

    /**
     * Сохранить текущее изображение в файле
     * @param formatName Формат файла
     */
    public void saveFile(final String formatName){
        if (images==null) return;
        Iterator<ImageWriter>iter=ImageIO.getImageWritersByFormatName(formatName);
        ImageWriter writer=iter.next();
        var chooser=new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        String[] extensions=writer.getOriginatingProvider().getFileSuffixes();
        chooser.setFileFilter(new FileNameExtensionFilter("Image files", extensions));

        int r=chooser.showSaveDialog(this);
        if (r!=JFileChooser.APPROVE_OPTION) return;
        File f=chooser.getSelectedFile();
        try{
            ImageOutputStream imageOut=ImageIO.createImageOutputStream(f);
            writer.setOutput(imageOut);

            writer.write(new IIOImage(images[0],null,null));
            for (int i = 1; i < images.length; i++) {
                var iioImage=new IIOImage(images[i],null,null);
                if (writer.canInsertImage(i))
                    writer.writeInsert(i,iioImage,null);
            }
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(this,e);
        }
    }

    /**
     * Получает ряд предпочтительных названий форматов из всех средств записи.
     * Предпочтительным считается первое название формата, указываемое средством записи
     * @return Возвращает ряд названий форматов
     */
    public static Set<String>getWriterFormats(){
        TreeSet<String>writerFormats =new TreeSet<>();
        var formatNames=new TreeSet<>(
                Arrays.asList(ImageIO.getWriterFormatNames()));
        while (formatNames.size()>0){
            String name=formatNames.iterator().next();
            Iterator<ImageWriter>iter=ImageIO.getImageWritersByFormatName(name);
            ImageWriter writer=iter.next();
            String[]names=writer.getOriginatingProvider().getFormatNames();
            String format=names[0];
            if (format.equals(format.toLowerCase()))
                format=format.toUpperCase();
            writerFormats.add(format);
            formatNames.removeAll(Arrays.asList(names));
        }
        return writerFormats;
    }
}