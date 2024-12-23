import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('UserGroup e2e test', () => {
  const userGroupPageUrl = '/user-group';
  const userGroupPageUrlPattern = new RegExp('/user-group(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const userGroupSample = { name: 'spirit' };

  let userGroup;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/user-groups+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/user-groups').as('postEntityRequest');
    cy.intercept('DELETE', '/api/user-groups/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (userGroup) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/user-groups/${userGroup.id}`,
      }).then(() => {
        userGroup = undefined;
      });
    }
  });

  it('UserGroups menu should load UserGroups page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('user-group');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('UserGroup').should('exist');
    cy.url().should('match', userGroupPageUrlPattern);
  });

  describe('UserGroup page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(userGroupPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create UserGroup page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/user-group/new$'));
        cy.getEntityCreateUpdateHeading('UserGroup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', userGroupPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/user-groups',
          body: userGroupSample,
        }).then(({ body }) => {
          userGroup = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/user-groups+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [userGroup],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(userGroupPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details UserGroup page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('userGroup');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', userGroupPageUrlPattern);
      });

      it('edit button click should load edit UserGroup page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserGroup');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', userGroupPageUrlPattern);
      });

      it('edit button click should load edit UserGroup page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserGroup');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', userGroupPageUrlPattern);
      });

      it('last delete button click should delete instance of UserGroup', () => {
        cy.intercept('GET', '/api/user-groups/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('userGroup').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', userGroupPageUrlPattern);

        userGroup = undefined;
      });
    });
  });

  describe('new UserGroup page', () => {
    beforeEach(() => {
      cy.visit(`${userGroupPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('UserGroup');
    });

    it('should create an instance of UserGroup', () => {
      cy.get(`[data-cy="name"]`).type('even warming');
      cy.get(`[data-cy="name"]`).should('have.value', 'even warming');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        userGroup = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', userGroupPageUrlPattern);
    });
  });
});
