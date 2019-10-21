package pl.allegrotech.productsshop.domain

import pl.allegrotech.productsshop.infrastructure.InMemoryProductRepository
import spock.lang.Specification

class ProductFacadeSpec extends Specification {

    def inMemoryProductRepository = new InMemoryProductRepository()
    ProductFacade productFacade = new ProductFacadeImpl(inMemoryProductRepository)

    def "should create product and store in repository" () {
        given: "requested document"
            def requestedDocument = new ProductRequestDto("997", "unit_test_document")

        when: "request document storage"
            def savedDocument = productFacade.create(requestedDocument)

        then: "check if requested document has been saved"
            savedDocument.getName() == "unit_test_document"

        and: "requested document is stored in db"
            inMemoryProductRepository.findById(savedDocument.getId()).get().getName() == "unit_test_document"
    }
}