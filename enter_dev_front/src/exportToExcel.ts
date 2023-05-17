// exportToExcel.ts
import * as XLSX from 'xlsx';
const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';
export class ExcelService {
    constructor() { }
    static toExportFileName(excelFileName: string): string {
        return `${excelFileName}-${new Date().getTime()}.xlsx`;
    }
    public exportAsExcelFile(json: any[], name:string, titleArr:string[], sheetName:string): void {
        // const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(json);
        // const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
        // XLSX.writeFile(workbook, ExcelService.toExportFileName(excelFileName));

        /* convert state to workbook */
        var data = new Array();
        var keyArray = new Array();
        const getLength = function (obj) {
            var count = 0;
            for (var i in obj) {
                if (obj.hasOwnProperty(i)) {
                    count++;
                }
            }
            return count;
        };
        for (const key1 in json) {
            if (json.hasOwnProperty(key1)) {
                const element = json[key1];
                var rowDataArray = new Array();
                for (const key2 in element) {
                    if (element.hasOwnProperty(key2)) {
                        const element2 = element[key2];
                        rowDataArray.push(element2);
                        if (keyArray.length < getLength(element)) {
                            keyArray.push(key2);
                        }
                    }
                }
                data.push(rowDataArray);
            }
        }
        // keyArray为英文字段表头
        data.splice(0, 0, keyArray, titleArr);
        console.log('data', data);
        const ws = XLSX.utils.aoa_to_sheet(data);
        const wb = XLSX.utils.book_new();
        // 此处隐藏英文字段表头
        var wsrows = [{ hidden: true }];
        ws['!rows'] = wsrows; // ws - worksheet
        XLSX.utils.book_append_sheet(wb, ws, sheetName);
        /* generate file and send to client */
        XLSX.writeFile(wb, name + '.xlsx');

    }
}
