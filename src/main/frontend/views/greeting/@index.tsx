import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DrawerToggle, Grid, GridColumn, TextField } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { GreetingService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import ViewToolbar from 'Frontend/components/ViewToolbar';
import ToolbarComponentGroup from 'Frontend/components/ToolbarComponentGroup';

export const config: ViewConfig = {
  title: 'Greetings from Hilla',
  menu: {
    icon: 'vaadin:cube',
    order: 1,
    title: 'Greetings (Hilla)',
  },
};

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
  timeStyle: 'medium',
});

export default function GreetingView() {
  const dataProvider = useDataProvider(GreetingService);
  const name = useSignal('');
  const greet = async () => {
    // TODO This is not really React-like, more like a Java dev trying to write React code for the first time.
    try {
      await GreetingService.greet(name.value);
      dataProvider.refresh();
      name.value = '';
      Notification.show('Greeting added', { duration: 3000, position: 'bottom-end', theme: 'success' });
    } catch (error) {
      handleError(error);
    }
  };
  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Greetings from Hilla">
        <ToolbarComponentGroup>
          <TextField
            placeholder="What is your name?"
            maxlength={255}
            value={name.value}
            onValueChanged={(evt) => (name.value = evt.detail.value)}
          />
          <Button onClick={greet} theme="primary">
            Greet
          </Button>
        </ToolbarComponentGroup>
        <Button onClick={dataProvider.refresh}>Refresh</Button>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn path="greeting" />
        <GridColumn path="greetingDate" header="Date">
          {({ item }) => dateFormatter.format(new Date(item.greetingDate))}
        </GridColumn>
      </Grid>
    </main>
  );
}
