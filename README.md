# Walking Skeleton for Vaadin Apps

The idea behind this walking skeleton is to get a minimal Vaadin application up and running that allows a user to
perform a small end-to-end function. It has minimal implementations of the most important building blocks in place,
and they are connected in the same way as they would be in a real-world application.

It should be possible to generate a walking skeleton from [Vaadin Start](https://start.vaadin.com). This skeleton will
then be the starting point for _all_ tutorials and how-to guides in the documentation.

## Current Features

- Feature based package organization
    - This also makes it easy to delete the dummy features after a project has been generated
- [JSpecify](https://jspecify.dev/) nullability annotations
- JPA and in-memory H2 for persistence
    - There will be a how-to guide explaining how to add Flyway migration
    - There will be a how-to guide explaining how to replace H2 with PostgreSQL
    - There will be a how-to guide explaining how to add Jooq to the project
- Router layout with dynamic navigation view based on `AppLayout`, implemented in Flow
- Custom error handler for Flow
- Empty theme and Lumo utility classes
- Example service that can be called from both Flow and Hilla
- Example view with both Flow and Hilla implementations
- No security
    - There will be a how-to guide explaining how to add security (login, logout, authorization, audit logging)
- No internationalization
    - There will be a how-to guide explaining how to add internationalization

## Planned Features

- Example integration test for the service
- Example tests for the views
- Code formatter for the Maven project
- Native image build
