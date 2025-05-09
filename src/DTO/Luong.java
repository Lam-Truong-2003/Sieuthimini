package DTO;

public class Luong {
    private int maL, soNGAYCONG;
    private String maNV, thangnam, ngaychotluong;
    
    // Sử dụng float thay vì BigDecimal cho các giá trị tiền tệ
    private float tienluongcoban, tienphat, phucap, tienthuong, tongluong,soGIOLAM;

    // Getter và setter cho các trường kiểu int
    public float getSoGIOLAM() {
        return soGIOLAM;
    }
    public void setSoGIOLAM(float soGIOLAM) {
        this.soGIOLAM = soGIOLAM;
    }
    
    public int getSoNGAYCONG() {
        return soNGAYCONG;
    }
    public void setSoNGAYCONG(int soNGAYCONG) {
        this.soNGAYCONG = soNGAYCONG;
    }
    
    public int getMaL() {
        return maL;
    }
    public void setMaL(int maL) {
        this.maL = maL;
    }

    // Getter và setter cho các trường kiểu String
    public String getNgaychotluong() {
        return ngaychotluong;
    }
    public void setNgaychotluong(String ngaychotluong) {
        this.ngaychotluong = ngaychotluong;
    }

    public String getThangnam() {
        return thangnam;
    }
    public void setThangnam(String thangnam) {
        this.thangnam = thangnam;
    }

    public String getMaNV() {
        return maNV;
    }
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    // Getter và setter cho các trường kiểu float
    public float getTienthuong() {
        return tienthuong;
    }
    public void setTienthuong(float tienthuong) {
        this.tienthuong = tienthuong;
    }

    public float getTienluongcoban() {
        return tienluongcoban;
    }
    public void setTienluongcoban(float tienluongcoban) {
        this.tienluongcoban = tienluongcoban;
    }

    public float getPhucap() {
        return phucap;
    }
    public void setPhucap(float phucap) {
        this.phucap = phucap;
    }

    public float getTienphat() {
        return tienphat;
    }
    public void setTienphat(float tienphat) {
        this.tienphat = tienphat;
    }

    public float getTongluong() {
        return tongluong;
    }
    public void setTongluong(float tongluong) {
        this.tongluong = tongluong;
    }
}
