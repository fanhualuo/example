package com.hehe.demo.test1.excel.fastexcel;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Date: 2017/10/11
 * Time: 上午11:20
 * Author: xieqinghe .
 */
public class Workbook {
    private final String applicationName;
    private final String applicationVersion;
    private final List<Worksheet> worksheets = new ArrayList();
    private final StringCache stringCache = new StringCache();
    private final StyleCache styleCache = new StyleCache();
    private final ZipOutputStream os;
    private final Writer writer;

    public Workbook(OutputStream os, String applicationName, String applicationVersion) {
        this.os = new ZipOutputStream(os, Charset.forName("UTF-8"));
        this.writer = new Writer(this.os);
        this.applicationName = (String)Objects.requireNonNull(applicationName);
        if(applicationVersion != null && !applicationVersion.matches("\\d{1,2}\\.\\d{1,4}")) {
            throw new IllegalArgumentException("Application version must be of the form XX.YYYY");
        } else {
            this.applicationVersion = applicationVersion;
        }
    }

    public void finish() throws IOException {
        if(this.worksheets.isEmpty()) {
            throw new IllegalArgumentException("A workbook must contain at least one worksheet.");
        } else {
            Iterator var1 = this.worksheets.iterator();

            while(var1.hasNext()) {
                Worksheet ws = (Worksheet)var1.next();
                ws.finish();
            }

            this.writeFile("[Content_Types].xml", (w) -> {
                w.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\"><Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/><Default Extension=\"xml\" ContentType=\"application/xml\"/><Override PartName=\"/xl/sharedStrings.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml\"/><Override PartName=\"/xl/styles.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml\"/><Override PartName=\"/xl/workbook.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml\"/>");
                Iterator var2 = this.worksheets.iterator();

                while(var2.hasNext()) {
                    Worksheet ws = (Worksheet)var2.next();
                    w.append("<Override PartName=\"/xl/worksheets/sheet").append(this.getIndex(ws)).append(".xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\"/>");
                }

                w.append("<Override PartName=\"/docProps/core.xml\" ContentType=\"application/vnd.openxmlformats-package.core-properties+xml\"/><Override PartName=\"/docProps/app.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.extended-properties+xml\"/></Types>");
            });
            this.writeFile("docProps/app.xml", (w) -> {
                w.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Properties xmlns=\"http://schemas.openxmlformats.org/officeDocument/2006/extended-properties\"><Application>").appendEscaped(this.applicationName).append("</Application>").append(this.applicationVersion == null?"":"<AppVersion>" + this.applicationVersion + "</AppVersion>").append("</Properties>");
            });
            this.writeFile("docProps/core.xml", (w) -> {
                w.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><cp:coreProperties xmlns:cp=\"http://schemas.openxmlformats.org/package/2006/metadata/core-properties\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><dcterms:created xsi:type=\"dcterms:W3CDTF\">").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("Z</dcterms:created><dc:creator>").appendEscaped(this.applicationName).append("</dc:creator></cp:coreProperties>");
            });
            this.writeFile("_rels/.rels", (w) -> {
                w.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\"><Relationship Id=\"rId3\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties\" Target=\"docProps/app.xml\"/><Relationship Id=\"rId2\" Type=\"http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties\" Target=\"docProps/core.xml\"/><Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"xl/workbook.xml\"/></Relationships>");
            });
            this.writeFile("xl/workbook.xml", (w) -> {
                w.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><workbook xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"><workbookPr date1904=\"false\"/><bookViews><workbookView activeTab=\"0\"/></bookViews><sheets>");
                Iterator var2 = this.worksheets.iterator();

                while(var2.hasNext()) {
                    Worksheet ws = (Worksheet)var2.next();
                    w.append("<sheet name=\"").appendEscaped(ws.getName()).append("\" r:id=\"rId").append(this.getIndex(ws) + 2).append("\" sheetId=\"").append(this.getIndex(ws)).append("\"/>");
                }

                w.append("</sheets></workbook>");
            });
            this.writeFile("xl/_rels/workbook.xml.rels", (w) -> {
                w.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\"><Relationship Id=\"rId1\" Target=\"sharedStrings.xml\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings\"/><Relationship Id=\"rId2\" Target=\"styles.xml\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles\"/>");
                Iterator var2 = this.worksheets.iterator();

                while(var2.hasNext()) {
                    Worksheet ws = (Worksheet)var2.next();
                    w.append("<Relationship Id=\"rId").append(this.getIndex(ws) + 2).append("\" Target=\"worksheets/sheet").append(this.getIndex(ws)).append(".xml\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\"/>");
                }

                w.append("</Relationships>");
            });
            StringCache var10002 = this.stringCache;
            this.stringCache.getClass();
            this.writeFile("xl/sharedStrings.xml", var10002::write);
            StyleCache var3 = this.styleCache;
            this.styleCache.getClass();
            this.writeFile("xl/styles.xml", var3::write);
            this.os.finish();
        }
    }

    void writeFile(String name, ThrowingConsumer<Writer> consumer) throws IOException {
        ZipOutputStream var3 = this.os;
        synchronized(this.os) {
            this.os.putNextEntry(new ZipEntry(name));
            consumer.accept(this.writer);
            this.writer.flush();
            this.os.closeEntry();
        }
    }

    CachedString cacheString(String s) {
        return this.stringCache.cacheString(s);
    }

    int mergeAndCacheStyle(int currentStyle, String numberingFormat, Font font, Fill fill, Border border, Alignment alignment) {
        return this.styleCache.mergeAndCacheStyle(currentStyle, numberingFormat, font, fill, border, alignment);
    }

    int cacheAlternateShadingFillColor(Fill fill) {
        return this.styleCache.cacheDxf(fill);
    }

    int getIndex(Worksheet ws) {
        List var2 = this.worksheets;
        synchronized(this.worksheets) {
            return this.worksheets.indexOf(ws) + 1;
        }
    }

    public Worksheet newWorksheet(String name) {
        String sheetName = name.replaceAll("[/\\\\\\?\\*\\]\\[\\:]", "-");
        if(sheetName.length() > 31) {
            sheetName = sheetName.substring(0, 31);
        }

        List var3 = this.worksheets;
        synchronized(this.worksheets) {
            int number = 1;

            for(Set names = (Set)this.worksheets.stream().map(Worksheet::getName).collect(Collectors.toSet()); names.contains(sheetName); ++number) {
                String suffix = String.format(Locale.ROOT, "_%d", new Object[]{Integer.valueOf(number)});
                if(sheetName.length() + suffix.length() > 31) {
                    sheetName = sheetName.substring(0, 31 - suffix.length()) + suffix;
                } else {
                    sheetName = sheetName + suffix;
                }
            }

            Worksheet worksheet = new Worksheet(this, sheetName);
            this.worksheets.add(worksheet);
            return worksheet;
        }
    }
}


