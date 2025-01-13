# Walking Skeleton for Vaadin Apps

The idea behind this walking skeleton is to get a minimal Vaadin application up and running that allows a user to
perform a small end-to-end function. It has minimal implementations of the most important building blocks in place,
and they are connected in the same way as they would be in a real-world application.

It should be possible to generate a walking skeleton from [Vaadin Start](https://start.vaadin.com). This skeleton will
then be the starting point for _all_ tutorials and meat-on-the-bones-guides (i.e. how-tos) in the documentation.

*Note, that the generated skeleton will not contain a readme file at all.*

## Current Features

- Feature based package organization
    - This also makes it easy to delete the dummy features after a project has been generated
- [JSpecify](https://jspecify.dev/) nullability annotations
- JPA and in-memory H2 for persistence
    - There will be a meat-on-the-bones-guide explaining how to add Flyway migration
    - There will be a meat-on-the-bones-guide explaining how to replace H2 with PostgreSQL
    - There will be a meat-on-the-bones-guide explaining how to add Jooq to the project
- Router layout with dynamic navigation view based on `AppLayout`, implemented in Flow
- Custom error handler for Flow
- Empty theme and Lumo utility classes
- Example service that can be called from both Flow and Hilla
- Example view with both Flow and Hilla implementations
- Example integration test for the service
- Example integration test for the Flow view
- Code formatter for the Maven project
- No native image build
    - There will be a meat-on-the-bones-guide explaining how to build a native image
- No security
    - There will be a meat-on-the-bones-guide explaining how to add security (login, logout, authorization, audit logging)
- No internationalization
    - There will be a meat-on-the-bones-guide explaining how to add internationalization

## Missing Features

- Integration test for the React view 

## Open Questions

- There is no router layout in React. Having two layouts in the same application makes things complicated. Still, if you
  intend to write your UI in React, the generated skeleton should contain one in React.
  - Should it be possible to customize the generated skeleton to use either a Flow or a React layout?
  - If so, should there still be a Flow view with a React layout, and a React view with a Flow layout?
- Can we get rid of the code formatter Maven plugin?
- Can we get rid of the Maven wrapper?
- What should the generated `.gitignore` file look like?