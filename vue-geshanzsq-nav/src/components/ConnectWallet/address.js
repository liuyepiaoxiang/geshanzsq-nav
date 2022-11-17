class Address {
    constructor(address){
        this.adress = address;
        this.addressStr = this.addressShift();
    }

    addressShift(address) {
        return `${this.address.slice(4)}...${this.address.slice(-4)}`
    }

    get address() {
        return this.address;
    }

    set address(address) {
        this.address = address;
        this.addressStr = this.addressShift();
    }

    get addressStr() {
        return this.addressStr;
    }
}