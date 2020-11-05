package ru.progmatik.main.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fias.FiasObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * ридер для больших XML-файлов.
 */

public class XMLFileReader implements AutoCloseable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private BufferedInputStream bis;
    private XMLStreamReader xmlStreamReader;
    private Unmarshaller jaxbUnmarshaller;
    private Class objClass;
    private Annotation ano;


    public XMLFileReader(File inFile, Class objClass) throws IOException, XMLStreamException, JAXBException {

        // подготовка к чтению файла
        bis = new BufferedInputStream(new FileInputStream(inFile));

        XMLInputFactory factory = XMLInputFactory.newInstance();

        xmlStreamReader = factory.createXMLStreamReader(bis);

        this.objClass = objClass;

        JAXBContext jaxbContext = JAXBContext.newInstance(objClass);

        jaxbUnmarshaller = jaxbContext.createUnmarshaller();

    }

    /**
     * метод читает из открытого в конструкторе потока заданное число объектов класса AddrObj
     * @param arraySize
     * @return
     * @throws XMLStreamException
     * @throws JAXBException
     */
    public List<FiasObject> readAddrObjFromStream(int arraySize) throws XMLStreamException, JAXBException {

        if(xmlStreamReader == null || !xmlStreamReader.hasNext()){
            return null;
        }

        // лист для результата
        List<FiasObject> list = new ArrayList<>();
        // указатель на текущую ноду
        FiasObject fiasObj = null;
        // счетчик чтений
        int count = 0;

        XmlRootElement annotation = (XmlRootElement) this.objClass.getAnnotation(XmlRootElement.class);
        String xmlElementName = annotation.name();
        // если в ридере еще есть что читать
        while (xmlStreamReader.hasNext()) {
            try {
                // если элемент стартовый и его имя node
                if (!(xmlStreamReader.isStartElement() &&
                        xmlStreamReader.getLocalName().equalsIgnoreCase(xmlElementName))) {
                    // читаем следующий
                    xmlStreamReader.next();
                }else{
                    // создаем объект тип LocalNode
                    fiasObj = (FiasObject) jaxbUnmarshaller.unmarshal(xmlStreamReader);
                    // добавляем в лист ноду
                    list.add(fiasObj);
                    // инкрементируем счетчик
                    count++;
                    if(count%arraySize == 0){
                        return list;
                    }
                }
                // если достигли установленного размера - возвращаем лист
            } catch (XMLStreamException e1) {
                logger.error("Read from XMLstream error XMLStreamException", e1);
                e1.printStackTrace();
            } catch (NumberFormatException ne){
                logger.error("Read from XMLstream error NumberFormatException", ne);
//                ne.printStackTrace();
                xmlStreamReader.next();
            }
        }
        // закрываем ридер, если больше нечего читать
        xmlStreamReader.close();
        // возвращаем лист с остатком
        return list;
    }

    /* /**
     * метод читает из открытого в конструкторе потока заданное число объектов класса House
     * @param arraySize
     * @return
     * @throws XMLStreamException
     * @throws JAXBException
     */
    /*public List<House> readHousesFromStream(int arraySize, final String logFIAS) throws XMLStreamException, JAXBException {

        if(xmlStreamReader == null || !xmlStreamReader.hasNext()){
            return null;
        }

        // лист для результата
        List<House> houseList = new ArrayList<>();
        // указатель на текущую ноду
        House house = null;
        // счетчик чтений
        int count = 0;

        BigInteger zeroBigInt = BigInteger.valueOf(0);
        // если в ридере еще есть что читать
        while (xmlStreamReader.hasNext()) {
            try {
                // если элемент стартовый и его имя node
                if (!(xmlStreamReader.isStartElement() &&
                        xmlStreamReader.getLocalName().equalsIgnoreCase("House"))) {
                    // читаем следующий
                    xmlStreamReader.next();
                }else{
                    // создаем объект тип LocalNode
                    house = (House) jaxbUnmarshaller.unmarshal(xmlStreamReader);
                    count++;
                    if(logFIAS == null || logFIAS.isEmpty()) {
                        if (house.getSTRSTATUS() == null){
                            house.setSTRSTATUS(zeroBigInt);
                        }
                        if (house.getSTRSTATUS().equals(zeroBigInt)) {
                            // добавляем в лист ноду
                            houseList.add(house);
                        }
                    }
                    else{
                        if(house.getHOUSEGUID().toString().equalsIgnoreCase(logFIAS))
                            houseList.add(house);
                    }

                    if (count % arraySize == 0) {
                        return houseList;
                    }
                }
                // если достигли установленного размера - возвращаем лист
            } catch (NumberFormatException ne){
                logger.error("Read house from XMLstream error NumberFormatException", ne);
                xmlStreamReader.next();
            } catch (XMLStreamException e1) {
                logger.error("Read house from XMLstream error", e1);
                e1.printStackTrace();
            } catch (UnmarshalException e2) {
                logger.error("Unmarshal house from XMLstream error", e2);
                e2.printStackTrace();
            }
        }
        // закрываем ридер, если больше нечего читать
        xmlStreamReader.close();
        // возвращаем лист с остатком
        return houseList;
    }*/

    @Override
    public void close() throws Exception {
        bis.close();
        xmlStreamReader.close();
    }

    /**
     * вспомогательный метод определения снаружи, что в ридере еще есть данные
     * @return
     */
    public boolean hasNext() {
        try {
            return xmlStreamReader.hasNext();
        } catch (XMLStreamException e) {
            logger.error("StreamReader hasNext error", e);
            e.printStackTrace();
            return false;
        }
    }


}