/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pixan.billing.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pixan.billing.entities.Invoice;

/**
 *
 * @author sotobotero
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
}
